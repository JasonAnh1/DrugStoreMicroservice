package com.jasonanh.inventoryservice.service;

import com.jasonanh.inventoryservice.dto.InventoryResponse;
import com.jasonanh.inventoryservice.model.CheckObj;
import com.jasonanh.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jasonanh.inventoryservice.model.Inventory;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public void AddInventory(Inventory inventory){
        inventoryRepository.save(inventory);
    }

        @Transactional(readOnly = true)
        public List<InventoryResponse> isInStock(List<CheckObj> checkList) {

        List<InventoryResponse> lstResponse = new LinkedList<>();
        for(CheckObj ck:checkList){
            Inventory inventory = inventoryRepository.findBySkuCodeAndPackType(ck.getSkuCode(),ck.getPackType());
            if(inventory!= null){
                InventoryResponse inventoryResponse = new InventoryResponse();
                inventoryResponse.setPackType(inventory.getPackType());
                inventoryResponse.setSkuCode(inventory.getSkuCode());
                inventoryResponse.setStock(inventory.getQuantity() >= ck.getQuantity());
                lstResponse.add(inventoryResponse);
            }
        }
            return lstResponse;
          /*  return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                    .map(inventory ->
                     InventoryResponse.builder()
                             .skuCode(inventory.getSkuCode())
                             .isStock(inventory.getQuantity() > 0)
                             .build()
                    ).toList();*/
        }
    public List<Inventory> findInvenByskuCode(String skucode){
        return inventoryRepository.findAllBySkuCode(skucode);

    }

}
