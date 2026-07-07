package com.sabrina.ecommerce.service;

import com.sabrina.ecommerce.entity.Category;
import com.sabrina.ecommerce.entity.Product;
import com.sabrina.ecommerce.repository.CategoryRepository;
import com.sabrina.ecommerce.repository.ProductRepository;
import com.sabrina.ecommerce.service.model.request.ProductRequest;
import com.sabrina.ecommerce.service.model.response.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private Category category;
    private ProductRequest productRequest;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Electronique");

        product = new Product();
        product.setId(1L);
        product.setName("iPhone 15");
        product.setDescription("Smartphone Apple");
        product.setPrice(new BigDecimal("999.99"));
        product.setStock(50);
        product.setCategory(category);

        productRequest = new ProductRequest();
        productRequest.setName("iPhone 15");
        productRequest.setDescription("Smartphone Apple");
        productRequest.setPrice(new BigDecimal("999.99"));
        productRequest.setStock(50);
        productRequest.setCategoryId(1L);
    }

    @Test
    void getProductById_shouldReturnProduct_whenExists() {
        // Mock
        doReturn(Optional.of(product)).when(productRepository).findById(product.getId());

        // When
        ProductResponse result = productService.getProductById(product.getId());

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(product.getName());
        assertThat(result.getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    void createProduct_shouldCreateAndReturnProduct() {
        // Mock
        doReturn(Optional.of(category)).when(categoryRepository).findById(category.getId());
        doReturn(product).when(productRepository).save(any(Product.class));

        // When
        ProductResponse result = productService.createProduct(productRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(productRequest.getName());
        assertThat(result.getPrice()).isEqualTo(productRequest.getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_shouldUpdateAndReturnProduct() {
        // Mock
        ProductRequest updateRequest = new ProductRequest();
        updateRequest.setName("iPhone 16");
        updateRequest.setDescription("Nouveau smartphone Apple");
        updateRequest.setPrice(new BigDecimal("1099.99"));
        updateRequest.setStock(30);
        updateRequest.setCategoryId(1L);

        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setName("iPhone 16");
        updatedProduct.setDescription("Nouveau smartphone Apple");
        updatedProduct.setPrice(new BigDecimal("1099.99"));
        updatedProduct.setStock(30);
        updatedProduct.setCategory(category);

        doReturn(Optional.of(product)).when(productRepository).findById(1L);
        doReturn(Optional.of(category)).when(categoryRepository).findById(1L);
        doReturn(updatedProduct).when(productRepository).save(any(Product.class));

        // When
        ProductResponse result = productService.updateProduct(1L, updateRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(updatedProduct.getName());
        assertThat(result.getPrice()).isEqualTo(updatedProduct.getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void deleteProduct_shouldDeleteProduct_whenExists() {
        // Mock
        doReturn(Optional.of(product)).when(productRepository).findById(1L);

        // When
        productService.deleteProduct(1L);

        // Then
        verify(productRepository, times(1)).delete(product);
    }
}
