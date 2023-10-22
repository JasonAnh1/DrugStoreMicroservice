package com.jasonanh.orderservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jasonanh.orderservice.dto.OrderAndListItemResponse;
import com.jasonanh.orderservice.dto.OrderNewRequest;
import com.jasonanh.orderservice.model.OrderNew;
import com.jasonanh.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
////    ap dung cb cho api nay moi khi no duoc goi
//    @CircuitBreaker(name = "inventory",fallbackMethod = "fallBackMethod")
//    @TimeLimiter(name = "inventory")
//    @Retry(name = "inventory")
//    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest){
//
//        return CompletableFuture.supplyAsync(()-> orderService.placeOrder(orderRequest));
//    }
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public String placeOrder(@RequestBody OrderRequest orderRequest){
//
//        return  orderService.placeOrder(orderRequest);
//    }
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String updateStatus(@RequestBody OrderNew orderRequest){

        return  orderService.changeOrderStatus(orderRequest);
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public List<OrderAndListItemResponse> getOrdersByUserId(@RequestParam Long userID){

        return  orderService.listOrderByUserId(userID);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<OrderAndListItemResponse> getOrders(){

        return  orderService.listOrder();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)

    public String placeOrderNew(@RequestBody OrderNewRequest orderRequest) throws JsonProcessingException {

        return  orderService.placeOrderNew(orderRequest);
    }
    //  khi api truoc day xay ra exeption thi runtimeEx tiep nhan ex
//    public CompletableFuture<String> fallBackMethod(OrderRequest orderRequest,RuntimeException runtimeException){
//        return CompletableFuture.supplyAsync(()->"Oops ! Something went wrong,please order after sometime");
//
//    }
}
