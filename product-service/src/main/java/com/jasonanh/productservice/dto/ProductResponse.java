package com.jasonanh.productservice.dto;

import com.jasonanh.productservice.model.DiseaseType;
import com.jasonanh.productservice.model.DrugType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imgUrl;
    private String skuCode;
    private DrugType drugType;
    private DiseaseType diseaseType;
}
