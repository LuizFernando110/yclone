package com.example.yclone.dto.videoPost;

import com.example.yclone.dto.image.ImageDTO;
import com.example.yclone.dto.video.VideoDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class VideoPostDTO {
    private UUID id;
    private LocalDateTime postedAt;
    private String title;
    private VideoDTO video;
    private ImageDTO thumbnail;
}
