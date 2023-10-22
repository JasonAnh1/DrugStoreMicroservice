package com.jasonanh.inventoryservice.dto;

import com.jasonanh.inventoryservice.model.PackType;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequest {
    private String skuCode;
    private Integer quantity;
    private Long price;
    private PackType packType;
}