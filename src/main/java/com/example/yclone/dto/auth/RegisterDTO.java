package com.example.yclone.dto.auth;

public record RegisterDTO (
        String userName,
        String password,
        String email
) {}
