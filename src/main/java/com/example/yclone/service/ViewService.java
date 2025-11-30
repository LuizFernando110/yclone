package com.example.yclone.service;

import com.example.yclone.models.User;
import com.example.yclone.models.VideoPost;
import com.example.yclone.models.View;
import com.example.yclone.repository.UserRepository;
import com.example.yclone.repository.VideoPostRepository;
import com.example.yclone.repository.ViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ViewService {
    @Autowired
    private ViewRepository viewRepository;

    @Autowired
    private VideoPostRepository videoPostRepository;

    @Autowired
    private UserRepository userRepository;

    public View registerView(UUID userId, UUID videoId) {
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        VideoPost video = videoPostRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        View view = new View();
        view.setUser(user);
        view.setVideo(video);
        view.setViewedAt(LocalDateTime.now());

        return viewRepository.save(view);
    }

    public void deleteView(UUID viewId) {
        if (!viewRepository.existsById(viewId)) {
            throw new RuntimeException("View not found");
        }
        viewRepository.deleteById(viewId);
    }

    public Map<String, Long> getViewsFromVideoPost(UUID videoPostId) {
        Long count = viewRepository.countByVideoId(videoPostId);
        Map<String, Long> result = new HashMap<>();
        result.put("views", count);
        return result;
    }
}
