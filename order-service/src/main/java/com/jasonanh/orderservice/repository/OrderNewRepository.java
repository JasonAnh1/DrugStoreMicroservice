package com.jasonanh.orderservice.repository;

import com.jasonanh.orderservice.model.OrderNew;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderNewRepository extends JpaRepository<OrderNew,Long> {
    OrderNew findOrderNewById(Long id);
    List<OrderNew> findAllByUserId(Long userId);
}
