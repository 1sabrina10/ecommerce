package com.sabrina.ecommerce.service;

import com.sabrina.ecommerce.entity.Category;
import com.sabrina.ecommerce.exception.ResourceNotFoundException;
import com.sabrina.ecommerce.repository.CategoryRepository;
import com.sabrina.ecommerce.service.model.request.CategoryRequest;
import com.sabrina.ecommerce.service.model.response.CategoryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private CategoryRequest categoryRequest;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Electronique");
        category.setDescription("Telephones, ordinateurs");

        categoryRequest = new CategoryRequest();
        categoryRequest.setName("Electronique");
        categoryRequest.setDescription("Telephones, ordinateurs");
    }

    @Test
    void getAllCategories_shouldReturnEmptyList_whenNoCategories() {
        // Mock
        doReturn(List.of()).when(categoryRepository).findAll();

        // When
        List<CategoryResponse> result = categoryService.getAllCategories();

        // Then
        assertThat(result).isNotNull().isEmpty();
    }

    @Test
    void getAllCategories_shouldReturnListOfCategories() {

        // Mock
        doReturn(List.of(category)).when(categoryRepository).findAll();

        // When
        List<CategoryResponse> result = categoryService.getAllCategories();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo("Electronique");
    }

    @Test
    void getCategoryById_shouldReturnCategory_whenExists() {
        // Mock
        doReturn(Optional.of(category)).when(categoryRepository).findById(1L);

        // When
        CategoryResponse result = categoryService.getCategoryById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Electronique");
    }

    @Test
    void createCategory_shouldCreateAndReturnCategory() {

        // Mock
        doReturn(category).when(categoryRepository).save(any(Category.class));

        // When
        CategoryResponse result = categoryService.createCategory(categoryRequest);

        // Then
        assertThat(result.getName()).isEqualTo("Electronique");
    }

    @Test
    void updateCategory_shouldUpdateAndReturnCategory() {

        // Mock
        doReturn(Optional.of(category)).when(categoryRepository).findById(1L);
        doReturn(category).when(categoryRepository).save(any(Category.class));

        // When
        CategoryResponse result = categoryService.updateCategory(1L, categoryRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(categoryRequest.getName());
        assertThat(result.getDescription()).isEqualTo(categoryRequest.getDescription());
    }

    @Test
    void updateCategory_shouldThrowException_whenCategoryNotFound() {

        // Mock
        doReturn(Optional.empty()).when(categoryRepository).findById(1L);

        // Then
        assertThatThrownBy(() ->
                categoryService.updateCategory(1L, categoryRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Catégorie introuvable");
    }

    @Test
    void deleteCategory_shouldDeleteCategory() {

        // Mock
        doReturn(Optional.of(category)).when(categoryRepository).findById(1L);

        // When
        categoryService.deleteCategory(1L);

        // Then
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    void deleteCategory_shouldThrowException_whenCategoryNotFound() {

        // Mock
        doReturn(Optional.empty()).when(categoryRepository).findById(1L);

        // Then
        assertThatThrownBy(() ->
                categoryService.deleteCategory(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Catégorie introuvable");
    }
}
