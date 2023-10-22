package com.jasonanh.productservice.dto;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequest {
    private Integer id;
    private String skuCode;
    private Integer quantity;
    private Long price;
    private PackType packType;
}
