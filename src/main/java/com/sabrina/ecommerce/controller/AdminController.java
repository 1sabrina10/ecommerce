package com.sabrina.ecommerce.controller;

import com.sabrina.ecommerce.controller.api.AdminApi;
import com.sabrina.ecommerce.controller.model.CategoryApiRequest;
import com.sabrina.ecommerce.controller.model.CategoryApiResponse;
import com.sabrina.ecommerce.controller.model.OrderApiResponse;
import com.sabrina.ecommerce.controller.model.ProductApiRequest;
import com.sabrina.ecommerce.controller.model.ProductApiResponse;
import com.sabrina.ecommerce.controller.model.UpdateOrderStatusRequest;
import com.sabrina.ecommerce.mapper.AdminApiMapper;
import com.sabrina.ecommerce.mapper.OrderApiMapper;
import com.sabrina.ecommerce.service.CategoryService;
import com.sabrina.ecommerce.service.OrderService;
import com.sabrina.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class AdminController implements AdminApi {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final OrderService orderService;

    public AdminController(ProductService productService, CategoryService categoryService, OrderService orderService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<ProductApiResponse> createProduct(ProductApiRequest productApiRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AdminApiMapper.toApiResponse(
                        productService.createProduct(
                                AdminApiMapper.toServiceRequest(productApiRequest)
                        )
                ));
    }

    @Override
    public ResponseEntity<ProductApiResponse> updateProduct(Integer id, ProductApiRequest productApiRequest) {
        return ResponseEntity.ok(
                AdminApiMapper.toApiResponse(
                        productService.updateProduct(
                                id.longValue(),
                                AdminApiMapper.toServiceRequest(productApiRequest)
                        )
                )
        );
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Integer id) {
        productService.deleteProduct(id.longValue());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CategoryApiResponse> createCategory(CategoryApiRequest categoryApiRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AdminApiMapper.toApiResponse(
                        categoryService.createCategory(
                                AdminApiMapper.toServiceRequest(categoryApiRequest)
                        )
                ));
    }

    @Override
    public ResponseEntity<CategoryApiResponse> updateCategory(Integer id, CategoryApiRequest categoryApiRequest) {
        return ResponseEntity.ok(
                AdminApiMapper.toApiResponse(
                        categoryService.updateCategory(
                                id.longValue(),
                                AdminApiMapper.toServiceRequest(categoryApiRequest)
                        )
                )
        );
    }

    @Override
    public ResponseEntity<Void> deleteCategory(Integer id) {
        categoryService.deleteCategory(id.longValue());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<OrderApiResponse>> getAllOrders() {
        return ResponseEntity.ok(
                orderService.getAllOrders()
                        .stream()
                        .map(OrderApiMapper::toApiResponse)
                        .toList()
        );
    }

    @Override
    public ResponseEntity<OrderApiResponse> updateOrderStatus(Integer id, UpdateOrderStatusRequest updateOrderStatusRequest) {
        return ResponseEntity.ok(
                OrderApiMapper.toApiResponse(
                        orderService.updateStatus(
                                id.longValue(),
                                updateOrderStatusRequest.getStatus()
                        )
                )
        );
    }
}
