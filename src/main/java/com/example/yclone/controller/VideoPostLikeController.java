package com.example.yclone.controller;

import com.example.yclone.dto.VideoPostLikeDTO;
import com.example.yclone.models.VideoPostLike;
import com.example.yclone.service.VideoPostLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/video-post-likes")
public class VideoPostLikeController {

    @Autowired
    private VideoPostLikeService videoPostLikeService;

    @PostMapping
    public ResponseEntity<?> registerLike(@RequestBody VideoPostLikeDTO dto) {

        VideoPostLike like = videoPostLikeService.registerVideoPostLike(dto.getUserId(),
                dto.getVideoId());
        return ResponseEntity.ok(like.getId());
    }

    @DeleteMapping("/{likeId}")
    public ResponseEntity<Void> deleteLike(@PathVariable UUID likeId) {
        videoPostLikeService.deleteView(likeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{videoPostId}/count")
    public ResponseEntity<Map<String, Long>> getLikeCount(@PathVariable UUID videoPostId) {
        Map<String, Long> response = videoPostLikeService.getLikeFromVideoPost(videoPostId);
        return ResponseEntity.ok(response);
    }
}
