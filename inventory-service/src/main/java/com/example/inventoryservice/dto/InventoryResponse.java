package com.example.inventoryservice.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponse {

    public String productName;

    public boolean isInStock;
}
