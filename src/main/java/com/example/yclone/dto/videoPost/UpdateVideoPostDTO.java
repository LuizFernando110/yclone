package com.example.yclone.dto.videoPost;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateVideoPostDTO {
    private String title;
    private UUID video;
    private UUID image;
}
