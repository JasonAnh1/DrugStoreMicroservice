package com.jasonanh.orderservice.dto;

import com.jasonanh.orderservice.model.PackType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponse {
    private String skuCode;
    private PackType packType;
    private boolean isStock;

}
