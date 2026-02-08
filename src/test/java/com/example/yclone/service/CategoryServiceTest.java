package com.example.yclone.service;

import com.example.yclone.dto.category.CategoryDTO;
import com.example.yclone.dto.category.CategoryDetailDTO;
import com.example.yclone.dto.category.CreateCategoryDTO;
import com.example.yclone.dto.category.UpdateCategoryDTO;
import com.example.yclone.models.Category;
import com.example.yclone.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    private Category category;
    private CategoryDTO categoryDTO;
    private CategoryDetailDTO categoryDetailDTO;

    private Long categoryId;

    @BeforeEach
    void setUp() {
        categoryId = 1L;
        category = new Category();
        category.setId(categoryId);
        category.setName("Teste");

        categoryDTO = new CategoryDTO();
        categoryDTO.setId(categoryId);
        categoryDTO.setName("Teste");

        categoryDetailDTO = new CategoryDetailDTO();
        categoryDetailDTO.setId(categoryId);
        categoryDetailDTO.setName("Teste");
    }

    @Test
    void testCreateCategory() {
        CreateCategoryDTO createDTO = new CreateCategoryDTO();
        createDTO.setName("Teste");

        when(modelMapper.map(createDTO, Category.class)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(modelMapper.map(category, CategoryDTO.class)).thenReturn(categoryDTO);

        CategoryDTO result = categoryService.createCategory(createDTO);

        assertNotNull(result);
        assertEquals(categoryDTO.getId(), result.getId());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testFindAll() {
        List<Category> categories = List.of(category);
        when(categoryRepository.findAll()).thenReturn(categories);
        when(modelMapper.map(category, CategoryDetailDTO.class)).thenReturn(categoryDetailDTO);

        List<CategoryDetailDTO> result = categoryService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(categoryDetailDTO.getId(), result.get(0).getId());
    }

    @Test
    void testGetCategoryById() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(modelMapper.map(category, CategoryDetailDTO.class)).thenReturn(categoryDetailDTO);

        CategoryDetailDTO result = categoryService.getCategoryById(categoryId);

        assertNotNull(result);
        assertEquals(categoryDetailDTO.getId(), result.getId());
    }

    @Test
    void testUpdatedCategory() {
        UpdateCategoryDTO updateDTO = new UpdateCategoryDTO();
        updateDTO.setName("Updated");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        doAnswer(invocation -> {
            category.setName("Updated");
            return null;
        }).when(modelMapper).map(updateDTO, category);

        when(categoryRepository.save(category)).thenReturn(category);
        when(modelMapper.map(category, CategoryDTO.class)).thenReturn(categoryDTO);

        CategoryDTO result = categoryService.UpdatedCategory(categoryId, updateDTO);

        assertNotNull(result);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testDeleteCategory() {
        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        categoryService.deleteCategory(categoryId);

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void testDeleteCategoryNotFound() {
        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> categoryService.deleteCategory(categoryId));

        assertEquals("Category not found", exception.getMessage());
    }
}
