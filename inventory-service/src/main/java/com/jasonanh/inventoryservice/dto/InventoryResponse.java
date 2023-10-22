package com.jasonanh.inventoryservice.dto;

import com.jasonanh.inventoryservice.model.PackType;
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
    private boolean isStock;
    private Long price;
    private PackType packType;
}
