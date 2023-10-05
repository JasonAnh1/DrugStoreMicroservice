package com.jasonanh.productservice;


import com.jasonanh.productservice.dto.ProductRequest;
import com.jasonanh.productservice.dto.ProductResponse;
import com.jasonanh.productservice.model.Product;
import com.jasonanh.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {


    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest)
    {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product "+product.getId()+" is saved");

    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products= productRepository.findAll();
       return products.stream().map(this::mapToProductResponse).collect(toList());
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
