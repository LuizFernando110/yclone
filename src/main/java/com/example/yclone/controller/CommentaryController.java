package com.example.yclone.controller;

import com.example.yclone.dto.commentary.CommentaryDTO;
import com.example.yclone.dto.commentary.CommentaryDetailDTO;
import com.example.yclone.dto.commentary.CreateCommentaryDTO;
import com.example.yclone.dto.commentary.UpdateCommentaryDTO;
import com.example.yclone.service.CommentaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/commentary")
@Tag(name = "Comentários", description = "Gerenciamento de comentários em vídeos")
public class CommentaryController {

    @Autowired
    private CommentaryService commentaryService;

    @Operation(
            summary = "Lista comentários de um vídeo",
            description = "Retorna todos os comentários associados ao vídeo informado."
    )
    @GetMapping("video/{videoId}")
    public List<CommentaryDTO> list(@PathVariable UUID videoId){
        return commentaryService.findAll(videoId);
    }

    @Operation(
            summary = "Busca um comentário pelo ID",
            description = "Retorna os detalhes completos de um comentário específico."
    )
    @GetMapping("/{id}")
    public CommentaryDetailDTO getById(@PathVariable UUID id){
        return commentaryService.getCommentaryById(id);
    }

    @Operation(
            summary = "Cria um novo comentário",
            description = "Registra um novo comentário utilizando os dados enviados no corpo da requisição."
    )
    @PostMapping
    public CommentaryDTO create(@RequestBody CreateCommentaryDTO dto) {
        return commentaryService.createCommentary(dto);
    }

    @Operation(
            summary = "Atualiza um comentário",
            description = "Atualiza o conteúdo de um comentário existente com base no seu ID."
    )
    @PutMapping("/{id}")
    public CommentaryDetailDTO update(@PathVariable UUID id,
                                      @RequestBody UpdateCommentaryDTO dto) {
        return commentaryService.updateCommentary(id, dto);
    }

    @Operation(
            summary = "Remove um comentário",
            description = "Deleta permanentemente um comentário específico com base no ID informado."
    )
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        commentaryService.deleteCommentaryById(id);
    }
}
