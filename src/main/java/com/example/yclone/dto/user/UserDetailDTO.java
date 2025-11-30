package com.example.yclone.dto.user;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDetailDTO {
    private UUID id;
    private String userName;
    private String email;
}
