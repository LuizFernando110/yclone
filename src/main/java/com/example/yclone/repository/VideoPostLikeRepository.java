package com.example.yclone.repository;

import com.example.yclone.models.VideoPost;
import com.example.yclone.models.VideoPostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VideoPostLikeRepository extends JpaRepository<VideoPostLike, UUID> {
    boolean existsByUserAndVideo(UUID userId, VideoPost video);
    long countByVideoId(UUID videoId);
}
