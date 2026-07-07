package com.sabrina.ecommerce.mapper;

import com.sabrina.ecommerce.entity.Category;
import com.sabrina.ecommerce.entity.Product;
import com.sabrina.ecommerce.service.model.request.ProductRequest;
import com.sabrina.ecommerce.service.model.response.ProductResponse;

public class ProductMapper {

    public static Product toEntity(ProductRequest request, Category category) {

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(category);
        return product;
    }

    public static ProductResponse toResponse(Product product){

        System.out.println("===== PRODUCT =====");
        System.out.println("ID : " + product.getId());
        System.out.println("NAME : " + product.getName());
        System.out.println("CATEGORY : " + product.getCategory());

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());

        if(product.getCategory() != null){
            System.out.println("CATEGORY ID : " + product.getCategory().getId());

            response.setCategoryId(product.getCategory().getId());
            response.setCategoryName(product.getCategory().getName());
        }

        return response;
    }
}
