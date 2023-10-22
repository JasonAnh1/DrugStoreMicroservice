package com.jasonanh.orderservice.repository;

import com.jasonanh.orderservice.model.OrderNewCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderNewCartRepository extends JpaRepository<OrderNewCart,Long> {
    List<OrderNewCart> findAllByOrderId(long id);
}
