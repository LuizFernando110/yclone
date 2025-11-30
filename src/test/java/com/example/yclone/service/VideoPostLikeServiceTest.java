package com.example.yclone.service;

import com.example.yclone.models.User;
import com.example.yclone.models.VideoPost;
import com.example.yclone.models.VideoPostLike;
import com.example.yclone.repository.UserRepository;
import com.example.yclone.repository.VideoPostLikeRepository;
import com.example.yclone.repository.VideoPostRepository;
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
class VideoPostLikeServiceTest {

    @InjectMocks
    private VideoPostLikeService videoPostLikeService;

    @Mock
    private VideoPostLikeRepository videoPostLikeRepository;

    @Mock
    private VideoPostRepository videoPostRepository;

    @Mock
    private UserRepository userRepository;

    private UUID userId;
    private UUID videoPostId;
    private User user;
    private VideoPost videoPost;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        videoPostId = UUID.randomUUID();

        user = new User();
        user.setId(userId);

        videoPost = new VideoPost();
        videoPost.setId(videoPostId);
    }

    @Test
    void testRegisterVideoPostLikeSuccess() {
        VideoPostLike like = new VideoPostLike();
        like.setUser(user);
        like.setVideo(videoPost);
        like.setLikedAt(LocalDateTime.now());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(videoPostRepository.findById(videoPostId)).thenReturn(Optional.of(videoPost));
        when(videoPostLikeRepository.save(any(VideoPostLike.class))).thenReturn(like);

        VideoPostLike result = videoPostLikeService.registerVideoPostLike(userId, videoPostId);

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(videoPost, result.getVideo());

        verify(videoPostLikeRepository, times(1)).save(any(VideoPostLike.class));
    }

    @Test
    void testRegisterVideoPostLikeUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                videoPostLikeService.registerVideoPostLike(userId, videoPostId));

        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void testRegisterVideoPostLikeVideoNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(videoPostRepository.findById(videoPostId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                videoPostLikeService.registerVideoPostLike(userId, videoPostId));

        assertEquals("Video not found", ex.getMessage());
    }

    @Test
    void testDeleteViewSuccess() {
        UUID likeId = UUID.randomUUID();
        when(videoPostLikeRepository.existsById(likeId)).thenReturn(true);
        doNothing().when(videoPostLikeRepository).deleteById(likeId);

        videoPostLikeService.deleteView(likeId);

        verify(videoPostLikeRepository, times(1)).deleteById(likeId);
    }

    @Test
    void testDeleteViewNotFound() {
        UUID likeId = UUID.randomUUID();
        when(videoPostLikeRepository.existsById(likeId)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                videoPostLikeService.deleteView(likeId));

        assertEquals("Video post like not found", ex.getMessage());
    }

    @Test
    void testGetLikeFromVideoPost() {
        when(videoPostLikeRepository.countByVideoId(videoPostId)).thenReturn(5L);

        Map<String, Long> response = videoPostLikeService.getLikeFromVideoPost(videoPostId);

        assertEquals(1, response.size());
        assertEquals(5L, response.get("likes"));
    }
}
