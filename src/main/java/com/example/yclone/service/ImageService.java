package com.example.yclone.service;

import com.example.yclone.dto.image.CreateImageDTO;
import com.example.yclone.dto.image.ImageDTO;
import com.example.yclone.dto.image.ImageDetailDTO;
import com.example.yclone.dto.image.UpdateImageDTO;
import com.example.yclone.models.Image;
import com.example.yclone.repository.ImageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Create
    public ImageDTO createImage(CreateImageDTO dto){
        Image image = modelMapper.map(dto, Image.class);
        Image imageSaved = imageRepository.save(image);

        return modelMapper.map(imageSaved, ImageDTO.class);
    }

    // List
    public List<ImageDTO> findByUserId(UUID userId) {
        List<Image> images = imageRepository.findByUserId(userId);
        return images.stream().map(image -> modelMapper
                .map(image, ImageDTO.class))
                .toList();
    }

    // Read
    public ImageDetailDTO getImageDetail(UUID id){
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));
        return modelMapper.map(image, ImageDetailDTO.class);
    }

    // Update
    public ImageDTO updateImage(UUID id, UpdateImageDTO dto){
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));
        Image updateImage = imageRepository.save(image);
        return modelMapper.map(updateImage, ImageDTO.class);
    }

    // Delete
    public void deleteImage(UUID id){
        if (!imageRepository.existsById(id)) {
            throw new RuntimeException("Image not found");
        }
        imageRepository.deleteById(id);
    }
}
