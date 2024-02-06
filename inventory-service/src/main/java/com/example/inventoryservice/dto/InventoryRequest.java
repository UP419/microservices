package com.example.inventoryservice.dto;


import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequest {

    private String productName;

    private Integer quantity;
}
