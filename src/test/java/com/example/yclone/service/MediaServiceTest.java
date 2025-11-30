package com.example.yclone.service;

import com.example.yclone.models.Image;
import com.example.yclone.models.User;
import com.example.yclone.models.Video;
import com.example.yclone.repository.ImageRepository;
import com.example.yclone.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class MediaServiceTest {

    @InjectMocks
    private MediaService mediaService;

    @Mock
    private MinioService minioService;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private ImageRepository imageRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(java.util.UUID.randomUUID());
        user.setUserName("teste");
    }

    @Test
    void testUploadVideo() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "video.mp4",
                "video/mp4",
                "dummy content".getBytes()
        );

        when(minioService.uploadFile(file)).thenReturn("video.mp4");
        when(videoRepository.save(any(Video.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Video video = mediaService.uploadVideo(file, user);

        assertNotNull(video);
        assertEquals("video.mp4", video.getFileName());
        assertEquals("/minio/video.mp4", video.getUrl());
        assertEquals(user, video.getUser());
        assertEquals(120.0, video.getDuration());
        assertEquals("1920x1080", video.getResolution());
        assertEquals("H.264", video.getCodec());

        verify(minioService, times(1)).uploadFile(file);
        verify(videoRepository, times(1)).save(video);
    }

    @Test
    void testUploadImage() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.jpg",
                "image/jpeg",
                "dummy content".getBytes()
        );

        when(minioService.uploadFile(file)).thenReturn("image.jpg");
        when(imageRepository.save(any(Image.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Image image = mediaService.uploadImage(file, user);

        assertNotNull(image);
        assertEquals("image.jpg", image.getFileName());
        assertEquals("/minio/image.jpg", image.getUrl());
        assertEquals(user, image.getUser());
        assertEquals(1920L, image.getWidth());
        assertEquals(1080L, image.getHeight());
        assertEquals("jpg", image.getFormat());

        verify(minioService, times(1)).uploadFile(file);
        verify(imageRepository, times(1)).save(image);
    }
}
