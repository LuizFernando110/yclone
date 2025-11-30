package com.example.yclone.dto.commentary;

import com.example.yclone.dto.user.UserDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CommentaryDTO {
    private UUID id;
    private UserDTO user;
    private String comment;
    private LocalDateTime postedAt;
}
