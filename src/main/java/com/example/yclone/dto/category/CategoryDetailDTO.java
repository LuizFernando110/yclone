package com.example.yclone.dto.category;

import com.example.yclone.dto.videoPost.VideoPostDTO;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CategoryDetailDTO {
    private Long id;
    private String name;
    private List<VideoPostDTO> videos;
}
