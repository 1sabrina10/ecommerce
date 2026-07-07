package com.sabrina.ecommerce.mapper;

import com.sabrina.ecommerce.entity.Order;
import com.sabrina.ecommerce.entity.OrderItem;
import com.sabrina.ecommerce.service.model.response.OrderItemResponse;
import com.sabrina.ecommerce.service.model.response.OrderResponse;
import java.util.ArrayList;

public class OrderMapper {

    public static OrderItemResponse toItemResponse(OrderItem item) {
        OrderItemResponse response = new OrderItemResponse();
        response.setId(item.getId());
        response.setProductName(item.getProduct().getName());
        response.setQuantity(item.getQuantity());
        response.setPrice(item.getPrice());
        return response;
    }

    public static OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setStatus(order.getStatus().name());
        response.setCreatedAt(order.getCreatedAt());
        if (order.getItems() != null) {
            response.setItems(order.getItems()
                    .stream()
                    .map(OrderMapper::toItemResponse)
                    .toList());
        } else {
            response.setItems(new ArrayList<>());
        }
        return response;
    }
}