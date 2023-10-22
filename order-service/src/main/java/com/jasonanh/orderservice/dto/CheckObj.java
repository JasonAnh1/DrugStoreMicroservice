package com.jasonanh.orderservice.dto;

import com.jasonanh.orderservice.model.PackType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckObj {
    String skuCode;
    PackType packType;
    int quantity;
}
