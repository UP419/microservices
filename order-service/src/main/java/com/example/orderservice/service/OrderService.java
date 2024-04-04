package com.example.orderservice.service;

import com.example.orderservice.dto.InventoryResponse;
import com.example.orderservice.dto.ItemDto;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.model.Item;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;

    @Transactional
    public void placeOrder(OrderRequest orderRequest) {
        List<Item> itemList = orderRequest.getOrderItemsList().stream().map(this::getItem).toList();
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderItemsList(itemList)
                .build();

        List<String> productNames = itemList.stream().map(item -> item.getProductName()).toList();

        List<InventoryResponse> productsInStock = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory"
                        , uriBuilder -> uriBuilder.queryParam("productNames", productNames).build())
                .retrieve().
                bodyToFlux(InventoryResponse.class).
                collectList().
                block();

        boolean everyProductInStock = productsInStock.stream().allMatch(inventoryResponse -> inventoryResponse.isInStock);

        //bodyToMono(InventoryResponse[].class)
        //.retrieve()
        //.block


        if (everyProductInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is out of stock");
        }
    }

    private Item getItem(ItemDto itemDto) {
        Item item = Item.builder()
                .price(itemDto.getPrice())
                .quantity(itemDto.getQuantity())
                .build();
        return item;
    }
}
