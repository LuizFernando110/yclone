package com.example.yclone.dto.commentary;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateCommentaryDTO {
    private UUID userId;
    private UUID videopostId;
    private String comment;
}
