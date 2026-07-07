package com.sabrina.ecommerce.mapper;

import com.sabrina.ecommerce.controller.model.PageProductApiResponse;
import com.sabrina.ecommerce.controller.model.ProductApiResponse;
import com.sabrina.ecommerce.service.model.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ProductApiMapper {

    public ProductApiResponse toApiResponse(ProductResponse product) {
        if (product == null) {
            return null;
        }
        Long id = product.getId();

        ProductApiResponse response = new ProductApiResponse();
        response.setId(id != null ? id.intValue() : null);
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice() != null ? product.getPrice().doubleValue() : null);
        response.setCategoryId(product.getCategoryId() != null ? product.getCategoryId().intValue() : null);
        return response;
    }

    public PageProductApiResponse toPageApiResponse(Page<ProductResponse> page) {
        PageProductApiResponse response = new PageProductApiResponse();
        response.setContent(page.getContent().stream()
                .map(this::toApiResponse)
                .toList());
        response.setTotalElements((int) page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setNumber(page.getNumber());
        response.setSize(page.getSize());
        response.setFirst(page.isFirst());
        response.setLast(page.isLast());
        return response;
    }

}
