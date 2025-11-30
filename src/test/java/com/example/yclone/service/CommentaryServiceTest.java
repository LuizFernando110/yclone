package com.example.yclone.service;

import com.example.yclone.dto.commentary.CommentaryDTO;
import com.example.yclone.dto.commentary.CommentaryDetailDTO;
import com.example.yclone.dto.commentary.CreateCommentaryDTO;
import com.example.yclone.dto.commentary.UpdateCommentaryDTO;
import com.example.yclone.models.Commentary;
import com.example.yclone.models.User;
import com.example.yclone.models.VideoPost;
import com.example.yclone.repository.CommentaryRepository;
import com.example.yclone.repository.UserRepository;
import com.example.yclone.repository.VideoPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class CommentaryServiceTest {

    @InjectMocks
    private CommentaryService commentaryService;

    @Mock
    private CommentaryRepository commentaryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VideoPostRepository videoPostRepository;

    @Mock
    private ModelMapper modelMapper;

    private UUID commentaryId;
    private UUID userId;
    private UUID videoId;

    private Commentary commentary;
    private User user;
    private VideoPost videoPost;
    private CommentaryDTO commentaryDTO;
    private CommentaryDetailDTO commentaryDetailDTO;

    @BeforeEach
    void setUp() {
        commentaryId = UUID.randomUUID();
        userId = UUID.randomUUID();
        videoId = UUID.randomUUID();

        commentary = new Commentary();
        commentary.setId(commentaryId);
        commentary.setComment("Teste");
        commentary.setPostedAt(LocalDateTime.now());

        user = new User();
        user.setId(userId);
        user.setUserName("TesteUser");

        videoPost = new VideoPost();
        videoPost.setId(videoId);

        commentary.setUser(user);
        commentary.setVideo(videoPost);

        commentaryDTO = new CommentaryDTO();
        commentaryDTO.setId(commentaryId);
        commentaryDTO.setComment("Teste");

        commentaryDetailDTO = new CommentaryDetailDTO();
        commentaryDetailDTO.setId(commentaryId);
        commentaryDetailDTO.setComment("Teste");
    }

    @Test
    void testCreateCommentary() {
        CreateCommentaryDTO createDTO = new CreateCommentaryDTO();
        createDTO.setComment("Teste");
        createDTO.setUserId(userId);
        createDTO.setVideopostId(videoId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(videoPostRepository.findById(videoId)).thenReturn(Optional.of(videoPost));
        when(commentaryRepository.save(any(Commentary.class))).thenReturn(commentary);
        when(modelMapper.map(commentary, CommentaryDTO.class)).thenReturn(commentaryDTO);

        CommentaryDTO result = commentaryService.createCommentary(createDTO);

        assertNotNull(result);
        assertEquals(commentaryDTO.getId(), result.getId());
        verify(commentaryRepository, times(1)).save(any(Commentary.class));
    }

    @Test
    void testCreateCommentaryUserNotFound() {
        CreateCommentaryDTO createDTO = new CreateCommentaryDTO();
        createDTO.setComment("Teste");
        createDTO.setUserId(userId);
        createDTO.setVideopostId(videoId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> commentaryService.createCommentary(createDTO));
    }

    @Test
    void testFindAll() {
        List<Commentary> list = List.of(commentary);
        when(commentaryRepository.findByVideoId(videoId)).thenReturn(list);
        when(modelMapper.map(commentary, CommentaryDTO.class)).thenReturn(commentaryDTO);

        List<CommentaryDTO> result = commentaryService.findAll(videoId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(commentaryDTO.getId(), result.get(0).getId());
    }

    @Test
    void testGetCommentaryById() {
        when(commentaryRepository.findById(commentaryId)).thenReturn(Optional.of(commentary));
        when(modelMapper.map(commentary, CommentaryDetailDTO.class)).thenReturn(commentaryDetailDTO);

        CommentaryDetailDTO result = commentaryService.getCommentaryById(commentaryId);

        assertNotNull(result);
        assertEquals(commentaryDetailDTO.getId(), result.getId());
    }

    @Test
    void testGetCommentaryByIdNotFound() {
        when(commentaryRepository.findById(commentaryId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class,
                () -> commentaryService.getCommentaryById(commentaryId));
    }

    @Test
    void testUpdateCommentary() {
        UpdateCommentaryDTO updateDTO = new UpdateCommentaryDTO();
        updateDTO.setComment("Atualizado");

        when(commentaryRepository.findById(commentaryId)).thenReturn(Optional.of(commentary));
        when(commentaryRepository.save(commentary)).thenReturn(commentary);
        when(modelMapper.map(commentary, CommentaryDetailDTO.class)).thenReturn(commentaryDetailDTO);

        CommentaryDetailDTO result = commentaryService.updateCommentary(commentaryId, updateDTO);

        assertNotNull(result);
        verify(commentaryRepository, times(1)).save(commentary);
    }

    @Test
    void testDeleteCommentaryById() {
        when(commentaryRepository.existsById(commentaryId)).thenReturn(true);

        commentaryService.deleteCommentaryById(commentaryId);

        verify(commentaryRepository, times(1)).deleteById(commentaryId);
    }

    @Test
    void testDeleteCommentaryByIdNotFound() {
        when(commentaryRepository.existsById(commentaryId)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> commentaryService.deleteCommentaryById(commentaryId));

        assertEquals("Comment not found", exception.getMessage());
    }
}
