package com.sabrina.ecommerce.service.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class ProductRequest {

    @NotBlank(message = "Nom obligatoire")
    private String name;

    private String description;

    @NotNull(message = "Prix obligatoire")
    @Positive(message = "Prix doit être positif")
    private BigDecimal price;

    @NotNull(message = "Stock obligatoire")
    @Positive(message = "Stock doit être positif")
    private Integer stock;

    @NotNull(message = "Catégorie obligatoire")
    private Long categoryId;

    public ProductRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
