package com.sabrina.ecommerce.controller;

import com.sabrina.ecommerce.controller.api.CategoryApi;
import com.sabrina.ecommerce.controller.model.CategoryApiResponse;
import com.sabrina.ecommerce.mapper.CategoryApiMapper;
import com.sabrina.ecommerce.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class CategoryController implements CategoryApi {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public ResponseEntity<List<CategoryApiResponse>> getAllCategories() {
        List<CategoryApiResponse> categories = categoryService.getAllCategories()
                .stream()
                .map(CategoryApiMapper::toApiResponse)
                .toList();
        return ResponseEntity.ok(categories);
    }

    @Override
    public ResponseEntity<CategoryApiResponse> getCategoryById(Integer id) {
        return ResponseEntity.ok(
                CategoryApiMapper.toApiResponse(categoryService.getCategoryById(id.longValue()))
        );
    }
}
