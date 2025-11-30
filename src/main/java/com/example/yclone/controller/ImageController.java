package com.example.yclone.controller;

import com.example.yclone.dto.image.CreateImageDTO;
import com.example.yclone.dto.image.ImageDTO;
import com.example.yclone.dto.image.ImageDetailDTO;
import com.example.yclone.dto.image.UpdateImageDTO;
import com.example.yclone.models.Image;
import com.example.yclone.models.User;
import com.example.yclone.repository.UserRepository;
import com.example.yclone.service.ImageService;
import com.example.yclone.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MediaService mediaService;

    @GetMapping("user/{userId}")
    public List<ImageDTO> list(@PathVariable UUID userId) {
        return imageService.findByUserId(userId);
    }

    @GetMapping("/{imageId}")
    public ImageDetailDTO getById(@PathVariable UUID imageId) {
        return imageService.getImageDetail(imageId);
    }

    @PostMapping
    public ImageDTO create(@RequestBody CreateImageDTO dto) {
        return imageService.createImage(dto);
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageDetailDTO> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") UUID userId
    ) throws Exception {

        if(file.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "File cannot be empty"
            );
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"
                ));

        Image image = mediaService.uploadImage(file, user);

        ImageDetailDTO dto = modelMapper.map(image, ImageDetailDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{imageId}")
    public ImageDTO update(@PathVariable UUID imageId,
                           @RequestBody UpdateImageDTO dto) {
        return imageService.updateImage(imageId, dto);
    }

    @DeleteMapping("/{id}")
    public  void delete(@PathVariable UUID id) {
        imageService.deleteImage(id);
    }
}
