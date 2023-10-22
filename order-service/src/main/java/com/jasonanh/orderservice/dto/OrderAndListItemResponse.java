package com.jasonanh.orderservice.dto;

import com.jasonanh.orderservice.model.OrderNewCart;
import com.jasonanh.orderservice.model.OrderStatus;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderAndListItemResponse {
    private String orderNumber;
    private OrderStatus status;
    private Date orderDate;
    List<OrderNewCart> items;

}
