package com.example.yclone.service;

import com.example.yclone.dto.commentary.CommentaryDTO;
import com.example.yclone.dto.commentary.CommentaryDetailDTO;
import com.example.yclone.dto.commentary.CreateCommentaryDTO;
import com.example.yclone.dto.commentary.UpdateCommentaryDTO;
import com.example.yclone.models.Commentary;
import com.example.yclone.repository.CommentaryRepository;
import com.example.yclone.repository.UserRepository;
import com.example.yclone.repository.VideoPostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CommentaryService {
    @Autowired
    private CommentaryRepository commentaryRepository;

    @Autowired
    private VideoPostRepository videoPostRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Create
    public CommentaryDTO createCommentary(CreateCommentaryDTO dto) {
        Commentary commentary = new Commentary();

        commentary.setPostedAt(LocalDateTime.now());
        commentary.setComment(dto.getComment());

        commentary.setUser(userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND
                ))
        );

        commentary.setVideo(videoPostRepository.findById(dto.getVideopostId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND
                ))
        );

        Commentary savedCommentary = commentaryRepository.save(commentary);
        return modelMapper.map(savedCommentary, CommentaryDTO.class);
    }

    // List
    public List<CommentaryDTO> findAll (UUID videoId) {
        List<Commentary> commentaries = commentaryRepository.findByVideoId(videoId);
        return commentaries.stream().map(commentary -> modelMapper
                .map(commentary, CommentaryDTO.class))
                .toList();
    }

    // Read
    public CommentaryDetailDTO getCommentaryById(UUID id) {
        Commentary commentary = commentaryRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Comment not found"));
        return modelMapper.map(commentary, CommentaryDetailDTO.class);
    }

    // Update
    public CommentaryDetailDTO updateCommentary(UUID id, UpdateCommentaryDTO dto) {
        Commentary commentary = commentaryRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Comment not found"));
        Commentary updatedCommentary = commentaryRepository.save(commentary);
        return modelMapper.map(updatedCommentary, CommentaryDetailDTO.class);
    }

    public void deleteCommentaryById(UUID id) {
        if (!commentaryRepository.existsById(id)) {
            throw new RuntimeException("Comment not found");
        }
        commentaryRepository.deleteById(id);
    }
}
