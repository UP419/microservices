package com.example.orderservice.dto;

import com.example.orderservice.model.ItemDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class OrderRequest {

    private List<ItemDto> orderItemsList;

}
