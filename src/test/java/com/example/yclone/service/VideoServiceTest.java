package com.example.yclone.service;

import com.example.yclone.dto.video.CreateVideoDTO;
import com.example.yclone.dto.video.UpdateVideoDTO;
import com.example.yclone.dto.video.VideoDTO;
import com.example.yclone.dto.video.VideoDetailDTO;
import com.example.yclone.models.Video;
import com.example.yclone.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class VideoServiceTest {

    @InjectMocks
    private VideoService videoService;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private ModelMapper modelMapper;

    private UUID userId;
    private UUID videoId;
    private Video video;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        videoId = UUID.randomUUID();

        video = new Video();
        video.setId(videoId);
    }

    @Test
    void testCreateVideo() {
        CreateVideoDTO dto = new CreateVideoDTO();
        dto.setFileName("video.mp4");

        Video savedVideo = new Video();
        savedVideo.setId(UUID.randomUUID());

        when(modelMapper.map(dto, Video.class)).thenReturn(video);
        when(videoRepository.save(video)).thenReturn(savedVideo);
        when(modelMapper.map(savedVideo, VideoDTO.class)).thenReturn(new VideoDTO());

        VideoDTO result = videoService.createVideo(dto);

        assertNotNull(result);
        verify(videoRepository, times(1)).save(video);
    }

    @Test
    void testFindByUserId() {
        Video v1 = new Video();
        Video v2 = new Video();
        List<Video> videos = List.of(v1, v2);

        when(videoRepository.findByUserId(userId)).thenReturn(videos);
        when(modelMapper.map(any(Video.class), eq(VideoDTO.class))).thenReturn(new VideoDTO());

        List<VideoDTO> result = videoService.findByUserId(userId);

        assertEquals(2, result.size());
        verify(videoRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testGetVideoByIdSuccess() {
        when(videoRepository.findById(videoId)).thenReturn(Optional.of(video));
        when(modelMapper.map(video, VideoDetailDTO.class)).thenReturn(new VideoDetailDTO());

        VideoDetailDTO result = videoService.getVideoById(videoId);

        assertNotNull(result);
        verify(videoRepository, times(1)).findById(videoId);
    }

    @Test
    void testGetVideoByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(videoRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> videoService.getVideoById(id));
        assertEquals("Video not found", ex.getMessage());
    }

    @Test
    void testUpdateVideo() {
        video.setFileName("old.mp4");
        UUID id = video.getId();

        when(videoRepository.findById(id)).thenReturn(Optional.of(video));
        when(videoRepository.save(video)).thenReturn(video);
        when(modelMapper.map(video, VideoDTO.class)).thenReturn(new VideoDTO());

        VideoDTO result = videoService.updateVideo(id, new UpdateVideoDTO());

        assertNotNull(result);
        verify(videoRepository, times(1)).save(video);
    }

    @Test
    void testDeleteVideoSuccess() {
        UUID id = video.getId();
        when(videoRepository.existsById(id)).thenReturn(true);
        doNothing().when(videoRepository).deleteById(id);

        videoService.deleteVideo(id);
        verify(videoRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteVideoNotFound() {
        UUID id = UUID.randomUUID();
        when(videoRepository.existsById(id)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> videoService.deleteVideo(id));
        assertEquals("Video not found", ex.getMessage());
    }
}
