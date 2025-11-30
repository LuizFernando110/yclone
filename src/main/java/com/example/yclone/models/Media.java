package com.example.yclone.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name= "file_name", nullable = false)
    private String fileName;

    @Column(name = "content_type",nullable = false)
    private String contentType;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Long size;

    @Column(name="uploaded_at", nullable = false)
    private LocalDateTime uploadedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
