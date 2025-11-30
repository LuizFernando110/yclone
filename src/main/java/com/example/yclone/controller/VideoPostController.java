package com.example.yclone.controller;

import com.example.yclone.dto.videoPost.CreateVideoPostDTO;
import com.example.yclone.dto.videoPost.UpdateVideoPostDTO;
import com.example.yclone.dto.videoPost.VideoPostDTO;
import com.example.yclone.dto.videoPost.VideoPostDetailDTO;
import com.example.yclone.service.VideoPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/video-post")
public class VideoPostController {
    @Autowired
    private VideoPostService videoPostService;

    @GetMapping
    public List<VideoPostDTO> list() {
        return videoPostService.findAll();
    }

    @GetMapping("/{id}")
    public VideoPostDetailDTO getById(@PathVariable UUID id){
        return videoPostService.getVideoPostById(id);
    }

    @PostMapping
    public VideoPostDTO create(@RequestBody CreateVideoPostDTO dto){
        return videoPostService.createVideoPost(dto);
    }

    @PutMapping("/{id}")
    public VideoPostDetailDTO update(@PathVariable UUID id,
                               @RequestBody UpdateVideoPostDTO dto){
        return videoPostService.updateVideoPost(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        videoPostService.deleteVideoPost(id);
    }
}
