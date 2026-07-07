package com.sabrina.ecommerce.mapper;

import com.sabrina.ecommerce.entity.Category;
import com.sabrina.ecommerce.service.model.request.CategoryRequest;
import com.sabrina.ecommerce.service.model.response.CategoryResponse;

public class CategoryMapper {

    public static Category toEntity(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return category;
    }

    public static CategoryResponse toResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        return response;
    }
}
