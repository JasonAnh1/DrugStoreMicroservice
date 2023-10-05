package com.jasonanh.productservice;

import com.jasonanh.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@RequiredArgsConstructor
public class ProductServiceApplication{
	private final ProductRepository productRepository;
	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

}
