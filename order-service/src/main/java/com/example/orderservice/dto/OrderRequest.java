package com.example.orderservice.dto;

import lombok.*;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private List<ItemDto> orderItemsList;

}
