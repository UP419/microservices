package com.example.orderservice.service;

import com.example.orderservice.dto.ItemDto;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.model.Item;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public void placeOrder(OrderRequest orderRequest) {
        List<Item> itemList = orderRequest.getOrderItemsList().stream().map(itemDto -> getItem(itemDto)).toList();
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderItemsList(itemList)
                .build();
        orderRepository.save(order);
    }

    private Item getItem(ItemDto itemDto) {
        Item item = Item.builder()
                .price(itemDto.getPrice())
                .quantity(itemDto.getQuantity())
                .build();
        return item;
    }
}
