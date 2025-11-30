package com.example.yclone.dto.user;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String userName;
}
