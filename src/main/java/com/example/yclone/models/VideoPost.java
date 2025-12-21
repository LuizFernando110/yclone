package com.example.yclone.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class VideoPost {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @ManyToOne
    @JoinColumn(name = "channel_id", nullable = false)
    private User channel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thumbnail_id")
    private Image thumbnail;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commentary> commentaries;

    @ManyToMany(mappedBy = "videos")
    private List<Category> categories;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<View> views;

    @Column(name = "posted_at", nullable = false)
    private LocalDateTime postedAt;
}
