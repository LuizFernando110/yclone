package com.example.yclone.service;

import com.example.yclone.models.User;
import com.example.yclone.models.VideoPost;
import com.example.yclone.models.View;
import com.example.yclone.repository.UserRepository;
import com.example.yclone.repository.VideoPostRepository;
import com.example.yclone.repository.ViewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ViewServiceTest {

    @InjectMocks
    private ViewService viewService;

    @Mock
    private ViewRepository viewRepository;

    @Mock
    private VideoPostRepository videoPostRepository;

    @Mock
    private UserRepository userRepository;

    private UUID userId;
    private UUID videoId;
    private UUID viewId;
    private User user;
    private VideoPost video;
    private View view;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        videoId = UUID.randomUUID();
        viewId = UUID.randomUUID();

        user = new User();
        user.setId(userId);

        video = new VideoPost();
        video.setId(videoId);

        view = new View();
        view.setId(viewId);
        view.setUser(user);
        view.setVideo(video);
        view.setViewedAt(LocalDateTime.now());
    }

    @Test
    void testRegisterViewSuccess() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(videoPostRepository.findById(videoId)).thenReturn(Optional.of(video));
        when(viewRepository.save(any(View.class))).thenReturn(view);

        View result = viewService.registerView(userId, videoId);

        assertNotNull(result);
        assertEquals(userId, result.getUser().getId());
        assertEquals(videoId, result.getVideo().getId());
        verify(viewRepository, times(1)).save(any(View.class));
    }

    @Test
    void testRegisterViewUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> viewService.registerView(userId, videoId));
        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void testRegisterViewVideoNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(videoPostRepository.findById(videoId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> viewService.registerView(userId, videoId));
        assertEquals("Video not found", ex.getMessage());
    }

    @Test
    void testDeleteViewSuccess() {
        when(viewRepository.existsById(viewId)).thenReturn(true);
        doNothing().when(viewRepository).deleteById(viewId);

        viewService.deleteView(viewId);

        verify(viewRepository, times(1)).deleteById(viewId);
    }

    @Test
    void testDeleteViewNotFound() {
        when(viewRepository.existsById(viewId)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> viewService.deleteView(viewId));
        assertEquals("View not found", ex.getMessage());
    }

    @Test
    void testGetViewsFromVideoPost() {
        when(viewRepository.countByVideoId(videoId)).thenReturn(5L);

        Map<String, Long> result = viewService.getViewsFromVideoPost(videoId);

        assertNotNull(result);
        assertEquals(5L, result.get("views"));
        verify(viewRepository, times(1)).countByVideoId(videoId);
    }
}
