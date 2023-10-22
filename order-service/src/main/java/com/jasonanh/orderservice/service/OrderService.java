package com.jasonanh.orderservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jasonanh.orderservice.dto.*;
import com.jasonanh.orderservice.event.OrderPlaceEvent;
import com.jasonanh.orderservice.model.*;
import com.jasonanh.orderservice.repository.OrderNewCartRepository;
import com.jasonanh.orderservice.repository.OrderNewRepository;
import com.jasonanh.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String,OrderPlaceEvent> kafkaTemplate;
    private final OrderNewRepository orderNewRepository;
    private final OrderNewCartRepository orderNewCartRepository;

    public String changeOrderStatus(OrderNew request){
        OrderNew order= orderNewRepository.findOrderNewById(request.getId());
        if(order.getStatus() == OrderStatus.APPROVE){
            return "Đơn hàng hoàn thành không thể đổi trạng thái";
        }
        order.setStatus(request.getStatus());
        return "success";
    }
    public List<OrderAndListItemResponse> listOrder(){

        List<OrderNew> listOrder = orderNewRepository.findAll();
        return listOrder.stream().map(this::mapOrders).toList();
    }
    public List<OrderAndListItemResponse> listOrderByUserId(Long userId){

        List<OrderNew> listOrder = orderNewRepository.findAllByUserId(userId);
        return listOrder.stream().map(this::mapOrders).toList();
    }

    private OrderAndListItemResponse mapOrders(OrderNew orderNew) {
        OrderAndListItemResponse response = new OrderAndListItemResponse();
        response.setOrderNumber(orderNew.getOrderNumber());
        response.setStatus(orderNew.getStatus());
        response.setOrderDate(orderNew.getOrderDate());
        response.setItems(orderNewCartRepository.findAllByOrderId(orderNew.getId()));
        return response;
    }

    public String placeOrderNew(OrderNewRequest request) throws JsonProcessingException {
        OrderNew order = new OrderNew();

        order.setOrderNumber(UUID.randomUUID().toString());
        order.setStatus(OrderStatus.PROCESS);
        order.setOrderDate(new Date());
        order.setUserId(request.getUserId());
        List<OrderNewCart> orderNewCarts =request.getItems().stream().map(this::mapItems).toList();

        List<CheckObj> checkObjs  = new LinkedList<>();
        for(OrderNewCart items : orderNewCarts){
            CheckObj ck = new CheckObj();
            ck.setPackType(items.getPackType());
            ck.setSkuCode(items.getSkuCode());
            ck.setQuantity(items.getAmount());
            checkObjs.add(ck);

        }
        InventoryResponse[] inventoryResponses = webClientBuilder.build().post()
                .uri("http://inventory-service/api/inventory/check")
                .body(Mono.just(checkObjs),CheckObj.class)
                .retrieve()
                // xac dinh kieu tra ve
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean allProductInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isStock);
        if(allProductInStock){
            OrderNew finalOrder = orderNewRepository.save(order);
            for(OrderNewCart items : orderNewCarts){
                items.setOrderId(finalOrder.getId());
                items.setOrder(finalOrder);
                items.setPrice(items.getPrice()*items.getAmount());
            }
            orderNewCartRepository.saveAll(orderNewCarts);
            return "Order place successfully";
        }else {
            return "product is not in stock";
        }
    }
    ;
    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);
        List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

        
        InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                // xac dinh kieu tra ve
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean allProductInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isStock);
        if(allProductInStock){
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic",new OrderPlaceEvent(order.getOrderNumber()));
            return "Order place successfully";
        }else {
            throw new IllegalArgumentException("Product is not in stock!!");
        }


    }
//    private CheckObj checkObj (PackType packType,String skuCode){
//        CheckObj ckobj = new CheckObj();
//        ckobj.setSkuCode(skuCode);
//        ckobj.setPackType(packType);
//        return ckobj;
//    }
    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
    private OrderNewCart mapItems(OderItems listItem) {
        OrderNewCart orderNewCart = new OrderNewCart();
        orderNewCart.setPrice(listItem.getPrice());
        orderNewCart.setSkuCode(listItem.getSkuCode());
        orderNewCart.setAmount(listItem.getAmount());
        orderNewCart.setPackType(listItem.getPackType());
        return orderNewCart;
    }
}
