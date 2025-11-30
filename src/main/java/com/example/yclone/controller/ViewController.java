package com.example.yclone.controller;

import com.example.yclone.dto.ViewDTO;
import com.example.yclone.models.View;
import com.example.yclone.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/views")
public class ViewController {
    @Autowired
    private ViewService viewService;

    @GetMapping("/{videoPostId}/count")
    public ResponseEntity<Map<String, Long>> getViewCount(@PathVariable UUID videoPostId) {
        Map<String, Long> response = viewService.getViewsFromVideoPost(videoPostId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> registerView(@RequestBody ViewDTO dto) {
        View view = viewService.registerView(dto.getUserId(), dto.getVideoId());
        return ResponseEntity.ok(view.getId());
    }

    @DeleteMapping("/{viewId}")
    public ResponseEntity<?> deleteView(@PathVariable UUID viewId) {
        viewService.deleteView(viewId);
        return ResponseEntity.noContent().build();
    }
}
