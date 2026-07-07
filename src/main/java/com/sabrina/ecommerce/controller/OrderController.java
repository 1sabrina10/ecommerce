package com.sabrina.ecommerce.controller;

import com.sabrina.ecommerce.controller.api.OrderApi;
import com.sabrina.ecommerce.controller.model.OrderApiResponse;
import com.sabrina.ecommerce.controller.model.OrderItemApiRequest;
import com.sabrina.ecommerce.mapper.OrderApiMapper;
import com.sabrina.ecommerce.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class OrderController implements OrderApi {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @Override
    public ResponseEntity<OrderApiResponse> addItemToOrder(Integer id, OrderItemApiRequest orderItemApiRequest) {
        return ResponseEntity.ok(
                OrderApiMapper.toApiResponse(
                        orderService.addItem(id.longValue(), OrderApiMapper.toServiceRequest(orderItemApiRequest))
                )
        );
    }

    @Override
    public ResponseEntity<OrderApiResponse> createOrder() {
        return ResponseEntity.ok(
                OrderApiMapper.toApiResponse(orderService.createOrder(getCurrentUserEmail()))
        );
    }

    @Override
    public ResponseEntity<List<OrderApiResponse>> getMyOrders() {
        List<OrderApiResponse> orders = orderService.getMyOrders(getCurrentUserEmail())
                .stream()
                .map(OrderApiMapper::toApiResponse)
                .toList();
        return ResponseEntity.ok(orders);
    }

    @Override
    public ResponseEntity<OrderApiResponse> getOrderById(Integer id) {
        return ResponseEntity.ok(
                OrderApiMapper.toApiResponse(orderService.getOrderById(id.longValue()))
        );
    }
}