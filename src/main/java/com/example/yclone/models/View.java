package com.example.yclone.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "video_views")
public class View {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private VideoPost video;

    @Column(name = "viewed_at", nullable = false)
    private LocalDateTime viewedAt;
}
