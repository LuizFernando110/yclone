package com.example.yclone.repository;

import com.example.yclone.models.Commentary;
import com.example.yclone.models.VideoPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentaryRepository extends JpaRepository<Commentary, UUID> {
    List<Commentary> findByVideoId(UUID videoId);
    List<Commentary> findByUser(UUID userId);
    List<Commentary> findByVideoOrderByPostedAtDesc(VideoPost video);
}
