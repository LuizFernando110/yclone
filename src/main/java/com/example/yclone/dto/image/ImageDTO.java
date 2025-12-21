package com.example.yclone.dto.image;

import lombok.Data;

import java.util.UUID;

@Data
public class ImageDTO {
    private UUID id;
    private String url;
    private Long width;
    private Long height;
}
