package com.example.yclone.dto.video;

import com.example.yclone.dto.user.UserDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class VideoDTO {
    private UUID id;
    private UserDTO user;
    private String fileName;
    private Double duration;
    private String resolution;
    private LocalDateTime uploadedAt;
}
