package com.example.yclone.controller;

import com.example.yclone.dto.category.CategoryDTO;
import com.example.yclone.dto.category.CategoryDetailDTO;
import com.example.yclone.dto.category.CreateCategoryDTO;
import com.example.yclone.dto.category.UpdateCategoryDTO;
import com.example.yclone.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDetailDTO>  getCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/{categoryId}")
    public CategoryDetailDTO getCategory(@PathVariable UUID categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @PostMapping
    public CategoryDTO createCategory(@RequestBody CreateCategoryDTO dto) {
        return categoryService.createCategory(dto);
    }

    @PutMapping("/{categoryId}")
    public CategoryDTO updateCategory(@PathVariable UUID categoryId,
                                      @RequestBody UpdateCategoryDTO dto) {
        return categoryService.UpdatedCategory(categoryId, dto);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
