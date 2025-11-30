package com.example.yclone.service;

import com.example.yclone.dto.category.CategoryDTO;
import com.example.yclone.dto.category.CategoryDetailDTO;
import com.example.yclone.dto.category.CreateCategoryDTO;
import com.example.yclone.dto.category.UpdateCategoryDTO;
import com.example.yclone.models.Category;
import com.example.yclone.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Create
    public CategoryDTO createCategory(CreateCategoryDTO dto) {
        Category category = modelMapper.map(dto, Category.class);
        Category categorySaved =  categoryRepository.save(category);

        return modelMapper.map(categorySaved, CategoryDTO.class);
    }

    // List
    public List<CategoryDetailDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryDetailDTO.class))
                .toList();
    }

    // Read
    public CategoryDetailDTO getCategoryById(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return modelMapper.map(category, CategoryDetailDTO.class);
    }

    // Update
    public CategoryDTO UpdatedCategory(UUID id, UpdateCategoryDTO dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        modelMapper.map(dto, category);
        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    // Delete
    public void deleteCategory(UUID id) {
        if(!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }
        categoryRepository.deleteById(id);
    }
}
