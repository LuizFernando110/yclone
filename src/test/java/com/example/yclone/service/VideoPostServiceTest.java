package com.example.yclone.service;

import com.example.yclone.dto.videoPost.CreateVideoPostDTO;
import com.example.yclone.dto.videoPost.VideoPostDTO;
import com.example.yclone.dto.videoPost.VideoPostDetailDTO;
import com.example.yclone.models.Image;
import com.example.yclone.models.User;
import com.example.yclone.models.Video;
import com.example.yclone.models.VideoPost;
import com.example.yclone.repository.ImageRepository;
import com.example.yclone.repository.UserRepository;
import com.example.yclone.repository.VideoPostRepository;
import com.example.yclone.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class VideoPostServiceTest {

    @InjectMocks
    private VideoPostService videoPostService;

    @Mock
    private VideoPostRepository videoPostRepository;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    private UUID videoId;
    private UUID channelId;
    private UUID thumbnailId;
    private Video video;
    private User channel;
    private Image thumbnail;

    @BeforeEach
    void setUp() {
        videoId = UUID.randomUUID();
        channelId = UUID.randomUUID();
        thumbnailId = UUID.randomUUID();

        video = new Video();
        video.setId(videoId);

        channel = new User();
        channel.setId(channelId);

        thumbnail = new Image();
        thumbnail.setId(thumbnailId);
    }

//    @Test
//    void testCreateVideoPostSuccess() {
//        CreateVideoPostDTO dto = new CreateVideoPostDTO();
//        dto.setTitle("Test Title");
//        dto.setVideoId(videoId);
//        dto.setChannelId(channelId);
//        dto.setThumbnailId(thumbnailId);
//
//        VideoPost videoPost = new VideoPost();
//        videoPost.setPostedAt(LocalDateTime.now());
//        videoPost.setTitle(dto.getTitle());
//        videoPost.setVideo(video);
//        videoPost.setChannel(channel);
//        videoPost.setThumbnail(thumbnail);
//
//        VideoPost savedVideoPost = new VideoPost();
//        savedVideoPost.setId(UUID.randomUUID());
//
//        when(videoRepository.findById(videoId)).thenReturn(Optional.of(video));
//        when(userRepository.findById(channelId)).thenReturn(Optional.of(channel));
//        when(imageRepository.findById(thumbnailId)).thenReturn(Optional.of(thumbnail));
//        when(videoPostRepository.save(any(VideoPost.class))).thenReturn(savedVideoPost);
//        when(modelMapper.map(savedVideoPost, VideoPostDTO.class)).thenReturn(new VideoPostDTO());
//
//        VideoPostDTO result = videoPostService.createVideoPost(dto, channelId);
//
//        assertNotNull(result);
//        verify(videoPostRepository, times(1)).save(any(VideoPost.class));
//    }

//    @Test
//    void testCreateVideoPostVideoNotFound() {
//        CreateVideoPostDTO dto = new CreateVideoPostDTO();
//        dto.setVideoId(videoId);
//
//        when(videoRepository.findById(videoId)).thenReturn(Optional.empty());
//
//        assertThrows(org.springframework.web.server.ResponseStatusException.class, () ->
//                videoPostService.createVideoPost(dto));
//    }

    @Test
    void testFindAll() {
        VideoPost vp1 = new VideoPost();
        VideoPost vp2 = new VideoPost();
        List<VideoPost> videoPosts = List.of(vp1, vp2);

        when(videoPostRepository.findAll()).thenReturn(videoPosts);
        when(modelMapper.map(any(VideoPost.class), eq(VideoPostDTO.class))).thenReturn(new VideoPostDTO());

        List<VideoPostDTO> result = videoPostService.findAll();

        assertEquals(2, result.size());
        verify(videoPostRepository, times(1)).findAll();
    }

    @Test
    void testGetVideoPostByIdSuccess() {
        VideoPost vp = new VideoPost();
        vp.setId(UUID.randomUUID());

        when(videoPostRepository.findById(vp.getId())).thenReturn(Optional.of(vp));
        when(modelMapper.map(vp, VideoPostDetailDTO.class)).thenReturn(new VideoPostDetailDTO());

        VideoPostDetailDTO result = videoPostService.getVideoPostById(vp.getId());
        assertNotNull(result);
    }

    @Test
    void testGetVideoPostByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(videoPostRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> videoPostService.getVideoPostById(id));
    }

    @Test
    void testDeleteVideoPostSuccess() {
        UUID id = UUID.randomUUID();
        when(videoPostRepository.existsById(id)).thenReturn(true);
        doNothing().when(videoPostRepository).deleteById(id);

        videoPostService.deleteVideoPost(id);
        verify(videoPostRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteVideoPostNotFound() {
        UUID id = UUID.randomUUID();
        when(videoPostRepository.existsById(id)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> videoPostService.deleteVideoPost(id));
        assertEquals("Video post not found", ex.getMessage());
    }
}
