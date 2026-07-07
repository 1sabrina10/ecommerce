package com.sabrina.ecommerce.controller;

import com.sabrina.ecommerce.controller.api.ProductApi;
import com.sabrina.ecommerce.controller.model.PageProductApiResponse;
import com.sabrina.ecommerce.controller.model.ProductApiResponse;
import com.sabrina.ecommerce.mapper.ProductApiMapper;
import com.sabrina.ecommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController implements ProductApi {

    private final ProductService productService;
    private final ProductApiMapper productApiMapper;

    public ProductController(ProductService productService, ProductApiMapper productApiMapper) {
        this.productService = productService;
        this.productApiMapper = productApiMapper;
    }

    @Override
    public ResponseEntity<PageProductApiResponse> getAllProducts(Integer page, Integer size) {
        int p = page != null ? page : 0;
        int s = size != null ? size : 10;
        return ResponseEntity.ok(
                productApiMapper.toPageApiResponse(productService.getAllProducts(p, s))
        );
    }

    @Override
    public ResponseEntity<ProductApiResponse> getProductById(Integer id) {
        return ResponseEntity.ok(
                productApiMapper.toApiResponse(productService.getProductById(id.longValue()))
        );
    }

    @Override
    public ResponseEntity<PageProductApiResponse> getProductsByCategory(Integer categoryId, Integer page, Integer size) {
        int p = page != null ? page : 0;
        int s = size != null ? size : 10;
        return ResponseEntity.ok(
                productApiMapper.toPageApiResponse(
                        productService.getProductsByCategory(categoryId.longValue(), p, s)
                )
        );
    }
}
