package com.sabrina.ecommerce.mapper;

import com.sabrina.ecommerce.controller.model.CategoryApiResponse;
import com.sabrina.ecommerce.service.model.response.CategoryResponse;

public class CategoryApiMapper {

    public static CategoryApiResponse toApiResponse(CategoryResponse category) {
        if (category == null) {
            return null;
        }
        CategoryApiResponse response = new CategoryApiResponse();
        response.setId(category.getId() != null ? category.getId().intValue() : null);
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        return response;
    }
}
