package com.example.yclone.service;

import com.example.yclone.dto.video.CreateVideoDTO;
import com.example.yclone.dto.video.UpdateVideoDTO;
import com.example.yclone.dto.video.VideoDTO;
import com.example.yclone.dto.video.VideoDetailDTO;
import com.example.yclone.models.Video;
import com.example.yclone.repository.VideoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Create
    public VideoDTO createVideo(CreateVideoDTO dto){
        Video video = modelMapper.map(dto, Video.class);
        Video videoSaved = videoRepository.save(video);

        return modelMapper.map(videoSaved, VideoDTO.class);
    }

    // List
    public List<VideoDTO> findByUserId(UUID userId) {
        List<Video> videos = videoRepository.findByUserId(userId);
        return videos.stream().map(video -> modelMapper
                .map(video, VideoDTO.class))
                .toList();
    }

    // Read
    public VideoDetailDTO getVideoById(UUID id){
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));
        return modelMapper.map(video, VideoDetailDTO.class);
    }

    // Update
    public VideoDTO updateVideo(UUID id, UpdateVideoDTO dto){
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));
        Video updatedVideo = videoRepository.save(video);
        return modelMapper.map(updatedVideo, VideoDTO.class);
    }

    // Delete
    public void deleteVideo(UUID id){
        if (!videoRepository.existsById(id)) {
            throw new RuntimeException("Video not found");
        }
        videoRepository.deleteById(id);
    }
}
