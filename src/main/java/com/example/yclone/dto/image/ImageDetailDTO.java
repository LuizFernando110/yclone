package com.example.yclone.dto.image;

import com.example.yclone.dto.user.UserDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ImageDetailDTO {
    private UUID id;
    private UserDTO user;
    private String fileName;
    private Long width;
    private Long height;
    private String format;
    private LocalDateTime uploadedAt;
    private Long size;
    private String url;
    private String contentType;
}
