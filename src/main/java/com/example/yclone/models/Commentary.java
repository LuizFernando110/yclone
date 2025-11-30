package com.example.yclone.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Commentary {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "videopost_id", nullable = false)
    private VideoPost video;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comment;

    @Column(name = "posted_at", nullable = false)
    private LocalDateTime postedAt;
}
