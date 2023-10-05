package com.jasonanh.orderservice.service;

import com.jasonanh.orderservice.dto.InventoryResponse;
import com.jasonanh.orderservice.dto.OrderLineItemsDto;
import com.jasonanh.orderservice.dto.OrderRequest;
import com.jasonanh.orderservice.event.OrderPlaceEvent;
import com.jasonanh.orderservice.model.Order;
import com.jasonanh.orderservice.model.OrderLineItems;
import com.jasonanh.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String,OrderPlaceEvent> kafkaTemplate;

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

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
