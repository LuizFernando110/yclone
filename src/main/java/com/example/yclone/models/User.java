package com.example.yclone.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Username is mandatory")
    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    @Email(message = "Invalid Email")
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Column(nullable = false)
    private String password;
}
