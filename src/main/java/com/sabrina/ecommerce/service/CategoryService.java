package com.sabrina.ecommerce.service;

import com.sabrina.ecommerce.entity.Category;
import com.sabrina.ecommerce.exception.ResourceNotFoundException;
import com.sabrina.ecommerce.mapper.CategoryMapper;
import com.sabrina.ecommerce.repository.CategoryRepository;
import com.sabrina.ecommerce.service.model.request.CategoryRequest;
import com.sabrina.ecommerce.service.model.response.CategoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> getAllCategories(){
        log.info("récupération de toutes les catégories");
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toResponse)
                .toList();
    }

    public CategoryResponse getCategoryById(Long id){
        log.info("Récupération de la catégorie {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie introuvable"));
         return CategoryMapper.toResponse(category);
    }

    public CategoryResponse createCategory(CategoryRequest request){
        log.info("Création de la catégorie : {}",request.getName());
        Category category = CategoryMapper.toEntity(request);
        Category saved = categoryRepository.save(category);
        return CategoryMapper.toResponse(saved);
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest request){
        log.info("Modification de la catégorie {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie introuvable"));
                category.setName(request.getName());
                category.setDescription(request.getDescription());
                Category updated = categoryRepository.save(category);
                return CategoryMapper.toResponse(updated);
    }

    public void deleteCategory(Long id){
        log.info("Suppression de la catégorie {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie introuvable"));
                categoryRepository.delete(category);
    }
}
