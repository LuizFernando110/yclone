package com.example.yclone.service;

import com.example.yclone.dto.category.CategoryDTO;
import com.example.yclone.dto.category.CategoryDetailDTO;
import com.example.yclone.dto.category.CreateCategoryDTO;
import com.example.yclone.dto.category.UpdateCategoryDTO;
import com.example.yclone.models.Category;
import com.example.yclone.models.VideoPost;
import com.example.yclone.repository.CategoryRepository;
import com.example.yclone.repository.VideoPostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final VideoPostRepository videoPostRepository;

    public CategoryService(
            CategoryRepository categoryRepository,
            ModelMapper modelMapper,
            VideoPostRepository videoPostRepository
    ) {
        this.videoPostRepository = videoPostRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    // Create
    @CacheEvict(value = "category", allEntries = true)
    public CategoryDTO createCategory(CreateCategoryDTO dto) {
        Category category = modelMapper.map(dto, Category.class);
        Category categorySaved =  categoryRepository.save(category);

        return modelMapper.map(categorySaved, CategoryDTO.class);
    }

    @Cacheable(value = "category")
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
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

    public CategoryDetailDTO addVideoPost(UUID id, List<UUID> videoPostIds) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new HttpStatusCodeException(HttpStatus.NOT_FOUND, "Category not found") {
                });

        if (videoPostIds == null || videoPostIds.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Video posts cannot be empty"
            );
        }

        List<VideoPost> videos = videoPostRepository.findAllById(videoPostIds);

        category.getVideos().addAll(videos);
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryDetailDTO.class);
    }

    // Delete
    public void deleteCategory(UUID id) {
        if(!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }
        categoryRepository.deleteById(id);
    }
}
