package com.example.yclone.service;

import com.example.yclone.models.User;
import com.example.yclone.models.VideoPost;
import com.example.yclone.models.VideoPostLike;
import com.example.yclone.repository.UserRepository;
import com.example.yclone.repository.VideoPostLikeRepository;
import com.example.yclone.repository.VideoPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class VideoPostLikeService {
    @Autowired
    private VideoPostLikeRepository videoPostLikeRepository;

    @Autowired
    private VideoPostRepository videoPostRepository;

    @Autowired
    private UserRepository userRepository;

    public VideoPostLike registerVideoPostLike(UUID userId, UUID videoPostId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        VideoPost video = videoPostRepository.findById(videoPostId)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        VideoPostLike vdp = new VideoPostLike();
        vdp.setUser(user);
        vdp.setVideo(video);
        vdp.setLikedAt(LocalDateTime.now());

        return videoPostLikeRepository.save(vdp);
    }

    public void deleteView(UUID vdpId) {
        if (!videoPostLikeRepository.existsById(vdpId)) {
            throw new RuntimeException("Video post like not found");
        }
        videoPostLikeRepository.deleteById(vdpId);
    }

    public Map<String, Long> getLikeFromVideoPost(UUID videoPostId) {
        Long count = videoPostLikeRepository.countByVideoId(videoPostId);

        Map<String, Long> response = new HashMap<>();
        response.put("likes", count);

        return response;
    }

}
