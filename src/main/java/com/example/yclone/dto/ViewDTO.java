package com.example.yclone.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ViewDTO {
    private UUID userId;
    private UUID videoId;
}
