package com.jasonanh.productservice.controller;

import com.jasonanh.productservice.ProductService;
import com.jasonanh.productservice.dto.InventoryRequest;
import com.jasonanh.productservice.dto.ProductRequest;
import com.jasonanh.productservice.dto.ProductResponse;
import com.jasonanh.productservice.model.DiseaseType;
import com.jasonanh.productservice.model.DrugType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController
{
    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest)
    {
        productService.createProduct(productRequest);
    }
    @PostMapping("/inven")
    @ResponseStatus(HttpStatus.CREATED)
    public String addToInventory(@RequestBody InventoryRequest request)
    {
        productService.addToInven(request);
        return "good";
    }
    @GetMapping("/getbyskucode")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryRequest> getInvenBySkuCode(@RequestParam String skuCode)
    {
        return productService.getAllInvenBySkuCode(skuCode);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts()
    {
        return productService.getAllProducts();
    }

    @GetMapping("/relates")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getProductRelate(@RequestParam(required = false)  DrugType drugType, @RequestParam(required= false) DiseaseType diseaseType) {return productService.getAllRelate(drugType,diseaseType);}
}
