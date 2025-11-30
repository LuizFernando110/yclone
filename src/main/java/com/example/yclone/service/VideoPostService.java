package com.example.yclone.service;

import com.example.yclone.dto.videoPost.CreateVideoPostDTO;
import com.example.yclone.dto.videoPost.UpdateVideoPostDTO;
import com.example.yclone.dto.videoPost.VideoPostDTO;
import com.example.yclone.dto.videoPost.VideoPostDetailDTO;
import com.example.yclone.models.VideoPost;
import com.example.yclone.repository.ImageRepository;
import com.example.yclone.repository.UserRepository;
import com.example.yclone.repository.VideoPostRepository;
import com.example.yclone.repository.VideoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VideoPostService {
    @Autowired
    private VideoPostRepository videoPostRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Create
    public VideoPostDTO createVideoPost(CreateVideoPostDTO dto) {

        VideoPost videoPost = new VideoPost();
        videoPost.setPostedAt(LocalDateTime.now());
        videoPost.setTitle(dto.getTitle());

        videoPost.setVideo(
                videoRepository.findById(dto.getVideoId())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Video Not Found"
                        ))
        );

        videoPost.setChannel(
                userRepository.findById(dto.getChannelId())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "User Not Found"
                        ))
        );

        videoPost.setThumbnail(
                imageRepository.findById(dto.getThumbnailId())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Image Not Found"
                        ))
        );

        VideoPost saved = videoPostRepository.save(videoPost);
        return modelMapper.map(saved, VideoPostDTO.class);
    }

    // List
    public List<VideoPostDTO> findAll() {
        List<VideoPost> videoPosts = videoPostRepository.findAll();
        return videoPosts.stream().map(videoPost -> modelMapper
                .map(videoPost, VideoPostDTO.class))
                .toList();
    }

    // Read
    public VideoPostDetailDTO getVideoPostById(UUID id) {
        VideoPost videoPost = videoPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video post not found"));
        return modelMapper.map(videoPost, VideoPostDetailDTO.class);
    }

    // Update
    public VideoPostDetailDTO updateVideoPost(UUID id, UpdateVideoPostDTO dto) {
        VideoPost videoPost = videoPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video post not found"));
        VideoPost updatedVideoPost = videoPostRepository.save(videoPost);
        return modelMapper.map(updatedVideoPost, VideoPostDetailDTO.class);
    }

    // Delete
    public void deleteVideoPost(UUID id) {
        if (!videoPostRepository.existsById(id)) {
            throw new RuntimeException("Video post not found");
        }
        videoPostRepository.deleteById(id);
    }
}
