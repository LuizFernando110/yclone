package com.example.yclone.repository;

import com.example.yclone.models.User;
import com.example.yclone.models.VideoPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VideoPostRepository extends JpaRepository<VideoPost, UUID> {
    List<VideoPost> findByChannel(UUID userId);
    List<VideoPost> findByTitleContainingIgnoreCase(String title);
    List<VideoPost> findAllByOrderByPostedAtDesc();
}
