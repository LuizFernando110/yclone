package com.example.yclone.repository;

import com.example.yclone.models.User;
import com.example.yclone.models.VideoPost;
import com.example.yclone.models.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ViewRepository extends JpaRepository<View, UUID> {
    long countByVideoId(UUID videoPostId);
    boolean existsByUserAndVideo(User user, VideoPost video);
}
