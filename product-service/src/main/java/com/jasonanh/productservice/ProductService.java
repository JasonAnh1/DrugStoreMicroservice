package com.jasonanh.productservice;


import com.jasonanh.productservice.dto.InventoryRequest;
import com.jasonanh.productservice.dto.ProductRequest;
import com.jasonanh.productservice.dto.ProductResponse;
import com.jasonanh.productservice.model.DiseaseType;
import com.jasonanh.productservice.model.DrugType;
import com.jasonanh.productservice.model.Product;
import com.jasonanh.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {


    private final ProductRepository productRepository;
    private final WebClient.Builder webClientBuilder;
    public void createProduct(ProductRequest productRequest)
    {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .imgUrl(productRequest.getImgUrl())
                .skuCode(productRequest.getSkuCode())
                .drugType(productRequest.getDrugType())
                .diseaseType(productRequest.getDiseaseType())
                .build();

        productRepository.save(product);
        log.info("Product "+product.getId()+" is saved");

    }
    public List<InventoryRequest> getAllInvenBySkuCode(String skucode){
        List<InventoryRequest> invenResponse = null;
        InventoryRequest[] inventoryResponses = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory/getbyskucode",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skucode).build())
                .retrieve()
                // xac dinh kieu tra ve
                .bodyToMono(InventoryRequest[].class)
                .block();
        assert inventoryResponses != null;
        invenResponse = Arrays.stream(inventoryResponses).toList();
        return invenResponse;

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
                .imgUrl(product.getImgUrl())
                .skuCode(product.getSkuCode())
                .drugType(product.getDrugType())
                .diseaseType(product.getDiseaseType())
                .build();
    }

    public void addToInven(InventoryRequest request) {
        webClientBuilder.build().post()
                .uri("http://inventory-service/api/inventory")
                .body(Mono.just(request),InventoryRequest.class)
                .retrieve()
                .bodyToMono(HttpStatus.class).block();
    }

    public List<ProductResponse> getAllRelate(DrugType drugType, DiseaseType diseaseType) {
        List<Product> products;
        if(drugType != null && diseaseType !=null){
            products = productRepository.findAllByDrugTypeAndDiseaseType(drugType,diseaseType);
        }else if(drugType != null){
            products = productRepository.findAllByDrugType(drugType);
        }else if(diseaseType != null){
            products = productRepository.findAllByDiseaseType(diseaseType);
        }else {
            products = productRepository.findAll();
        }

        return products.stream().map(this::mapToProductResponse).collect(toList());
    }
}
