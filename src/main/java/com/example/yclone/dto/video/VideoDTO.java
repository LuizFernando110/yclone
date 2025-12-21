package com.example.yclone.dto.video;

import lombok.Data;

import java.util.UUID;

@Data
public class VideoDTO {
    private UUID id;
    private String url;
    private Double duration;
    private String resolution;
}
