package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.InventoryRequest;
import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> productNames) {
        return inventoryRepository.findByProductNameIn(productNames).stream()
                .map(inventory -> InventoryResponse.builder()
                        .productName(inventory.getProductName())
                        .isInStock(inventory.getQuantity() > 0)
                        .build()).toList();
    }

    @Transactional
    public void addProduct(InventoryRequest inventoryRequest) {
        Inventory inventory = Inventory.builder()
                .productName(inventoryRequest.getProductName())
                .quantity(inventoryRequest.getQuantity())
                .build();
        inventoryRepository.save(inventory);
    }

}
