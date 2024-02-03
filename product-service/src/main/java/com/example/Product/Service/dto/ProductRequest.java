package com.example.Product.Service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotNull(message = "Product name cannot be blank")
    private String name;
    @NotNull(message = "Product description cannot be blank")
    private String description;
    @NotNull(message = "Product price cannot be null")
    private double price;
}
