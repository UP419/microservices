package com.example.inventoryservice.controller;

import com.example.inventoryservice.dto.InventoryRequest;
import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> productNames) {
        productNames.add("macBook");
        productNames.add("macbook");
        productNames.add("MacBook");
        productNames.add("Macbook");
        return inventoryService.isInStock(productNames);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addProduct(@RequestBody InventoryRequest inventoryRequest) {
        inventoryService.addProduct(inventoryRequest);
        return "Product added succesfully";
    }

}
