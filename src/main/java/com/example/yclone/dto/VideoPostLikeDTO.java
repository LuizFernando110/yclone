package com.example.yclone.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class VideoPostLikeDTO {
    private UUID userId;
    private UUID videoId;
}
