package com.sabrina.ecommerce.mapper;

import com.sabrina.ecommerce.controller.model.CategoryApiRequest;
import com.sabrina.ecommerce.controller.model.CategoryApiResponse;
import com.sabrina.ecommerce.controller.model.ProductApiRequest;
import com.sabrina.ecommerce.controller.model.ProductApiResponse;
import com.sabrina.ecommerce.service.model.request.CategoryRequest;
import com.sabrina.ecommerce.service.model.request.ProductRequest;
import com.sabrina.ecommerce.service.model.response.CategoryResponse;
import com.sabrina.ecommerce.service.model.response.ProductResponse;
import java.math.BigDecimal;

public class AdminApiMapper {

    // Product api.model → service.model
    public static ProductRequest toServiceRequest(ProductApiRequest api) {
        ProductRequest request = new ProductRequest();
        request.setName(api.getName());
        request.setDescription(api.getDescription());
        request.setPrice(api.getPrice() != null ?
                BigDecimal.valueOf(api.getPrice()) : null);
        request.setCategoryId(api.getCategoryId() != null ?
                api.getCategoryId().longValue() : null);
        return request;
    }

    //  Product service.model → api.model
    public static ProductApiResponse toApiResponse(ProductResponse service) {
        ProductApiResponse response = new ProductApiResponse();
        response.setId(service.getId() != null ?
                service.getId().intValue() : null);
        response.setName(service.getName());
        response.setDescription(service.getDescription());
        response.setPrice(service.getPrice() != null ?
                service.getPrice().doubleValue() : null);
        response.setCategoryId(service.getCategoryId() != null ?
                service.getCategoryId().intValue() : null);
        return response;
    }

    // Category api.model → service.model
    public static CategoryRequest toServiceRequest(CategoryApiRequest api) {
        CategoryRequest request = new CategoryRequest();
        request.setName(api.getName());
        request.setDescription(api.getDescription());
        return request;
    }

    // Category service.model → api.model
    public static CategoryApiResponse toApiResponse(CategoryResponse service) {
        CategoryApiResponse response = new CategoryApiResponse();
        response.setId(service.getId() != null ?
                service.getId().intValue() : null);
        response.setName(service.getName());
        response.setDescription(service.getDescription());
        return response;
    }
}
