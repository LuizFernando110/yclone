package com.example.yclone.controller;

import com.example.yclone.dto.video.CreateVideoDTO;
import com.example.yclone.dto.video.UpdateVideoDTO;
import com.example.yclone.dto.video.VideoDTO;
import com.example.yclone.dto.video.VideoDetailDTO;
import com.example.yclone.models.User;
import com.example.yclone.models.Video;
import com.example.yclone.repository.UserRepository;
import com.example.yclone.service.MediaService;
import com.example.yclone.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
public class VideoController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private UserRepository  userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MediaService mediaService;

    @GetMapping("user/{userId}")
    public List<VideoDTO> list(@PathVariable UUID userId) {
        return videoService.findByUserId(userId);
    }

    @GetMapping("/{videoId}")
    public VideoDetailDTO getById(@PathVariable UUID videoId){
        return videoService.getVideoById(videoId);
    }

    @PostMapping
    public VideoDTO create(@RequestBody CreateVideoDTO dto){
        return  videoService.createVideo(dto);
    }

    @PutMapping("/{videoId}")
    public VideoDTO update(@PathVariable UUID videoId,
                           @RequestBody UpdateVideoDTO dto){
        return videoService.updateVideo(videoId, dto);
    }

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<VideoDetailDTO> uploadVideo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") UUID userId
    ) throws Exception {

        if(file.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "File cannot be empty"
            );
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"
                ));

        Video video = mediaService.uploadVideo(file, user);

        VideoDetailDTO dto = modelMapper.map(video, VideoDetailDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @DeleteMapping("/{videoId}")
    public void delete(@PathVariable UUID videoId){
        videoService.deleteVideo(videoId);
    }

}
