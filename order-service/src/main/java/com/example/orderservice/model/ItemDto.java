package com.example.orderservice.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ItemDto {

    private BigDecimal price;

    private Integer quantity;
}
