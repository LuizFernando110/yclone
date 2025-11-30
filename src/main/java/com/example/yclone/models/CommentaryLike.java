package com.example.yclone.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class CommentaryLike {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "commentary_id", nullable = false)
    private Commentary commentary;

    @Column(nullable = false)
    private LocalDateTime likedAt;
}
