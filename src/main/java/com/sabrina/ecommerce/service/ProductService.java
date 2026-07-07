package com.sabrina.ecommerce.service;

import com.sabrina.ecommerce.entity.Category;
import com.sabrina.ecommerce.entity.Product;
import com.sabrina.ecommerce.exception.ResourceNotFoundException;
import com.sabrina.ecommerce.mapper.ProductMapper;
import com.sabrina.ecommerce.repository.CategoryRepository;
import com.sabrina.ecommerce.repository.ProductRepository;
import com.sabrina.ecommerce.service.model.request.ProductRequest;
import com.sabrina.ecommerce.service.model.response.ProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<ProductResponse> getAllProducts(int page, int size) {
        log.info("Récupération des produits page {} size {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable)
                .map(ProductMapper::toResponse);
    }

    public Page<ProductResponse> getProductsByCategory(Long categoryId, int page, int size) {
        log.info("Récupération des produits par catégorie {}", categoryId);
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByCategoryId(categoryId, pageable)
                .map(ProductMapper::toResponse);
    }

    public ProductResponse getProductById(Long id) {
        log.info("Récupération du produit {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit introuvable id : " + id));
        return ProductMapper.toResponse(product);
    }

    public ProductResponse createProduct(ProductRequest request) {
        log.info("Création du produit : {}", request.getName());
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie introuvable"));

        Product product = ProductMapper.toEntity(request, category);
        Product saved = productRepository.save(product);
        return ProductMapper.toResponse(saved);
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        log.info("Modification du produit {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit introuvable id : " + id));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie introuvable"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(category);

        Product updated = productRepository.save(product);
        return ProductMapper.toResponse(updated);
    }

    public void deleteProduct(Long id) {
        log.info("Suppression du produit {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit introuvable id : " + id));
        productRepository.delete(product);
    }


}
