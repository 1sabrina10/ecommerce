package com.sabrina.ecommerce.mapper;
import com.sabrina.ecommerce.controller.model.OrderApiResponse;
import com.sabrina.ecommerce.controller.model.OrderItemApiRequest;
import com.sabrina.ecommerce.controller.model.OrderItemApiResponse;
import com.sabrina.ecommerce.service.model.request.OrderItemRequest;
import com.sabrina.ecommerce.service.model.response.OrderItemResponse;
import com.sabrina.ecommerce.service.model.response.OrderResponse;
import java.math.BigDecimal;
import java.util.List;

public class OrderApiMapper {

    public static OrderItemRequest toServiceRequest(OrderItemApiRequest api) {
        OrderItemRequest service = new OrderItemRequest();
        service.setProductId(api.getProductId() != null ? api.getProductId().longValue() : null);
        service.setQuantity(api.getQuantity());
        return service;
    }

    private static OrderItemApiResponse toItemApiResponse(OrderItemResponse item) {
        OrderItemApiResponse response = new OrderItemApiResponse();
        response.setProductName(item.getProductName());
        response.setQuantity(item.getQuantity());
        response.setPrice(item.getPrice() != null ? item.getPrice().doubleValue() : null);
        return response;
    }

    public static OrderApiResponse toApiResponse(OrderResponse order) {
        OrderApiResponse response = new OrderApiResponse();
        response.setId(order.getId() != null ? order.getId().intValue() : null);
        response.setStatus(order.getStatus());

        if (order.getItems() != null) {
            List<OrderItemApiResponse> items = order.getItems().stream()
                    .map(OrderApiMapper::toItemApiResponse)
                    .toList();
            response.setItems(items);

            BigDecimal total = order.getItems().stream()
                    .map(item -> {
                        BigDecimal price = item.getPrice() != null ? item.getPrice() : BigDecimal.ZERO;
                        int qty = item.getQuantity() != null ? item.getQuantity() : 0;
                        return price.multiply(BigDecimal.valueOf(qty));
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            response.setTotalPrice(total.doubleValue());
        }

        return response;
    }
}