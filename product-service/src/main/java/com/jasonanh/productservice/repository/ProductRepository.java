package com.jasonanh.productservice.repository;


import com.jasonanh.productservice.model.DiseaseType;
import com.jasonanh.productservice.model.DrugType;
import com.jasonanh.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product,String> {
    List<Product> findAllByDrugTypeAndDiseaseType(DrugType drugType, DiseaseType diseaseType);
    List<Product> findAllByDrugType(DrugType drugType);
    List<Product> findAllByDiseaseType(DiseaseType diseaseType);
}
