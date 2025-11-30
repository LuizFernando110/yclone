package com.example.yclone.repository;

import com.example.yclone.models.Commentary;
import com.example.yclone.models.CommentaryLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentaryLikeRepository extends JpaRepository<CommentaryLike, UUID> {
    boolean existsByUserAndCommentary(UUID userId, UUID commentaryId);
    long countByCommentary(Commentary commentary);
}
