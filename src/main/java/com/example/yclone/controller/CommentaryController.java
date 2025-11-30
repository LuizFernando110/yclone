package com.example.yclone.controller;

import com.example.yclone.dto.commentary.CommentaryDTO;
import com.example.yclone.dto.commentary.CommentaryDetailDTO;
import com.example.yclone.dto.commentary.CreateCommentaryDTO;
import com.example.yclone.dto.commentary.UpdateCommentaryDTO;
import com.example.yclone.service.CommentaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/commentary")
public class CommentaryController {
    @Autowired
    private CommentaryService commentaryService;

    @GetMapping("video/{videoId}")
    public List<CommentaryDTO> list(@PathVariable UUID videoId){
        return commentaryService.findAll(videoId);
    }

    @GetMapping("/{id}")
    public CommentaryDetailDTO getById(@PathVariable UUID id){
        return commentaryService.getCommentaryById(id);
    }

    @PostMapping
    public CommentaryDTO create(@RequestBody CreateCommentaryDTO dto) {
        return commentaryService.createCommentary(dto);
    }

    @PutMapping("/{id}")
    public CommentaryDetailDTO update(@PathVariable UUID id,
                                      @RequestBody UpdateCommentaryDTO dto) {
        return commentaryService.updateCommentary(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        commentaryService.deleteCommentaryById(id);
    }
}
