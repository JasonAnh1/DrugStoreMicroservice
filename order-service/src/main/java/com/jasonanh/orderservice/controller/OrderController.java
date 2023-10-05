package com.jasonanh.orderservice.controller;

import com.jasonanh.orderservice.dto.OrderRequest;
import com.jasonanh.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    ap dung cb cho api nay moi khi no duoc goi
    public String placeOrder(@RequestBody OrderRequest orderRequest){

        return  orderService.placeOrder(orderRequest);
    }
    //  khi api truoc day xay ra exeption thi runtimeEx tiep nhan ex
//    public CompletableFuture<String> fallBackMethod(OrderRequest orderRequest,RuntimeException runtimeException){
//        return CompletableFuture.supplyAsync(()->"Oops ! Something went wrong,please order after sometime");
//
//    }
}
