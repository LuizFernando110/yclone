package com.example.yclone.dto.user;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateUserDTO {
    private String userName;

    @Email(message = "Email inválido")
    private String email;
}
