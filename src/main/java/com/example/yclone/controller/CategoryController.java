package com.example.yclone.controller;

import com.example.yclone.dto.category.CategoryDTO;
import com.example.yclone.dto.category.CategoryDetailDTO;
import com.example.yclone.dto.category.CreateCategoryDTO;
import com.example.yclone.dto.category.UpdateCategoryDTO;
import com.example.yclone.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Tag(name = "Category", description = "Gerenciamento de categorias")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @Operation(
            summary = "Lista todas as categorias existentes",
            description = """
                    Lista todas as categorias existentes, 
                    retornando apenas o nome de cada
                    """
    )
    public List<CategoryDetailDTO>  getCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/{categoryId}")
    @Operation(
            summary = "Retorna uma categoria via id",
            description = "Retorna detalhadamente todos os dados categoria via id"
    )
    public CategoryDetailDTO getCategory(@PathVariable Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @PostMapping
    @Operation(
            summary = "Cria uma nova categoria",
            description = """
                    Cria uma nova categoria através de um 
                    nome dado pelo corpo da requisição.
                    """
    )
    public CategoryDTO createCategory(@RequestBody CreateCategoryDTO dto) {
        return categoryService.createCategory(dto);
    }

    @PutMapping("/{categoryId}")
    @Operation(
            summary = "Altera uma categoria",
            description = """
                    Altera o nome de uma categoria através de um
                    nome dado pleo corpo da requisição.
                    """
    )
    public CategoryDTO updateCategory(@PathVariable Long categoryId,
                                      @RequestBody UpdateCategoryDTO dto) {
        return categoryService.UpdatedCategory(categoryId, dto);
    }

    @DeleteMapping("/{categoryId}")
    @Operation(
            summary = "Deleta uma categoria",
            description = """
                    Deleta o uma categoria via id passado na
                    requisição pós barra. 
                    """
    )
    public void deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
