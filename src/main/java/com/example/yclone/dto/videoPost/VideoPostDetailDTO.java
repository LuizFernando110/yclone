package com.example.yclone.dto.videoPost;

import com.example.yclone.dto.commentary.CommentaryDTO;
import com.example.yclone.dto.image.ImageDTO;
import com.example.yclone.dto.user.UserDTO;
import com.example.yclone.dto.video.VideoDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class VideoPostDetailDTO {
    private UUID id;
    private LocalDateTime postedAt;
    private String title;
    private UserDTO channel;
    private ImageDTO thumbnail;
    private VideoDTO video;
    private List<CommentaryDTO> commentaries;
}
