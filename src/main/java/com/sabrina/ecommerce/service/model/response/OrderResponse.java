package com.sabrina.ecommerce.service.model.response;

import com.sabrina.ecommerce.service.model.response.OrderItemResponse;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    private Long id;

    private String status;

    private LocalDateTime createdAt;

    private List<OrderItemResponse> items;

    public OrderResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }
}
