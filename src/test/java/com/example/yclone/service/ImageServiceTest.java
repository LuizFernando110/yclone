package com.example.yclone.service;

import com.example.yclone.dto.image.CreateImageDTO;
import com.example.yclone.dto.image.ImageDTO;
import com.example.yclone.dto.image.ImageDetailDTO;
import com.example.yclone.dto.image.UpdateImageDTO;
import com.example.yclone.models.Image;
import com.example.yclone.repository.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ImageServiceTest {

    @InjectMocks
    private ImageService imageService;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private ModelMapper modelMapper;

    private UUID imageId;
    private UUID userId;
    private Image image;
    private ImageDTO imageDTO;
    private ImageDetailDTO imageDetailDTO;

    @BeforeEach
    void setUp() {
        imageId = UUID.randomUUID();
        userId = UUID.randomUUID();

        image = new Image();
        image.setId(imageId);
        image.setFileName("teste.png");

        imageDTO = new ImageDTO();
        imageDTO.setId(imageId);
        imageDTO.setFileName("teste.png");

        imageDetailDTO = new ImageDetailDTO();
        imageDetailDTO.setId(imageId);
        imageDetailDTO.setFileName("teste.png");
    }

    @Test
    void testCreateImage() {
        CreateImageDTO createDTO = new CreateImageDTO();
        createDTO.setFileName("teste.png");

        when(modelMapper.map(createDTO, Image.class)).thenReturn(image);
        when(imageRepository.save(image)).thenReturn(image);
        when(modelMapper.map(image, ImageDTO.class)).thenReturn(imageDTO);

        ImageDTO result = imageService.createImage(createDTO);

        assertNotNull(result);
        assertEquals(imageDTO.getId(), result.getId());
        verify(imageRepository, times(1)).save(image);
    }

    @Test
    void testFindByUserId() {
        List<Image> images = List.of(image);
        when(imageRepository.findByUserId(userId)).thenReturn(images);
        when(modelMapper.map(image, ImageDTO.class)).thenReturn(imageDTO);

        List<ImageDTO> result = imageService.findByUserId(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(imageDTO.getId(), result.get(0).getId());
    }

    @Test
    void testGetImageDetail() {
        when(imageRepository.findById(imageId)).thenReturn(Optional.of(image));
        when(modelMapper.map(image, ImageDetailDTO.class)).thenReturn(imageDetailDTO);

        ImageDetailDTO result = imageService.getImageDetail(imageId);

        assertNotNull(result);
        assertEquals(imageDetailDTO.getId(), result.getId());
    }

    @Test
    void testGetImageDetailNotFound() {
        when(imageRepository.findById(imageId)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> imageService.getImageDetail(imageId));
        assertEquals("Image not found", exception.getMessage());
    }

    @Test
    void testUpdateImage() {
        UpdateImageDTO updateDTO = new UpdateImageDTO();
        updateDTO.setFileName("updated.png");

        when(imageRepository.findById(imageId)).thenReturn(Optional.of(image));
        when(imageRepository.save(image)).thenReturn(image);
        when(modelMapper.map(image, ImageDTO.class)).thenReturn(imageDTO);

        ImageDTO result = imageService.updateImage(imageId, updateDTO);

        assertNotNull(result);
        verify(imageRepository, times(1)).save(image);
    }

    @Test
    void testDeleteImage() {
        when(imageRepository.existsById(imageId)).thenReturn(true);

        imageService.deleteImage(imageId);

        verify(imageRepository, times(1)).deleteById(imageId);
    }

    @Test
    void testDeleteImageNotFound() {
        when(imageRepository.existsById(imageId)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> imageService.deleteImage(imageId));

        assertEquals("Image not found", exception.getMessage());
    }
}
