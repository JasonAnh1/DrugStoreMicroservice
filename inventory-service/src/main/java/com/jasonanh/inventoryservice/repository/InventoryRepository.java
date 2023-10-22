package com.jasonanh.inventoryservice.repository;

import com.jasonanh.inventoryservice.model.Inventory;
import com.jasonanh.inventoryservice.model.PackType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findBySkuCodeIn(List<String> skuCode);
    List<Inventory> findAllBySkuCode(String skuCode);
    Inventory findBySkuCodeAndPackType(String skucode, PackType packType);
}
