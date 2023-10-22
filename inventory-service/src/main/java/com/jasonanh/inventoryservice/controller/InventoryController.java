package com.jasonanh.inventoryservice.controller;

import com.jasonanh.inventoryservice.dto.InventoryRequest;
import com.jasonanh.inventoryservice.dto.InventoryResponse;
import com.jasonanh.inventoryservice.model.CheckObj;
import com.jasonanh.inventoryservice.model.Inventory;
import com.jasonanh.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestBody List<CheckObj> checkList){


        return inventoryService.isInStock(checkList);
    }
    @GetMapping("/getbyskucode")
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> findByskuCode(@RequestParam String skuCode){
        return inventoryService.findInvenByskuCode(skuCode);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void stockDrug(@RequestBody InventoryRequest inventory){
        Inventory i = new Inventory();
        i.setQuantity(inventory.getQuantity());
        i.setSkuCode(inventory.getSkuCode());
        i.setPrice(inventory.getPrice());
        i.setPackType(inventory.getPackType());
        inventoryService.AddInventory(i);
    }

}
