package com.jasonanh.orderservice.dto;

import com.jasonanh.orderservice.model.PackType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OderItems {
    private String skuCode;
    private PackType packType;
    private Long price;
    private int amount;
}
