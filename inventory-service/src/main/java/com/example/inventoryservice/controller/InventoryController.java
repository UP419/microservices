package com.example.inventoryservice.controller;

import com.example.inventoryservice.dto.InventoryRequest;
import com.example.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("{productName}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable String productName) {
        return inventoryService.isInStock(productName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addProduct(@RequestBody InventoryRequest inventoryRequest) {
        inventoryService.addProduct(inventoryRequest);
        return "Product added succesfully";
    }

}
