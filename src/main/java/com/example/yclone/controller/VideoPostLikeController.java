package com.example.yclone.controller;

import com.example.yclone.dto.VideoPostLikeDTO;
import com.example.yclone.models.VideoPostLike;
import com.example.yclone.service.VideoPostLikeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/video-post-likes")
public class VideoPostLikeController {

    @Autowired
    private VideoPostLikeService videoPostLikeService;

    @Operation(
            summary = "Registra um like em um vídeo",
            description = """
            Cria um registro de like para um usuário em um vídeo específico.
            Retorna o ID do like criado.
        """
    )
    @PostMapping
    public ResponseEntity<?> registerLike(@RequestBody VideoPostLikeDTO dto) {
        VideoPostLike like = videoPostLikeService.registerVideoPostLike(
                dto.getUserId(), dto.getVideoId());
        return ResponseEntity.ok(like.getId());
    }

    @Operation(
            summary = "Remove um like",
            description = """
            Exclui um like previamente registrado usando o ID do like.
            Retorna HTTP 204 quando concluído.
        """
    )
    @DeleteMapping("/{likeId}")
    public ResponseEntity<Void> deleteLike(@PathVariable UUID likeId) {
        videoPostLikeService.deleteView(likeId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Obtém a quantidade de likes de um vídeo",
            description = """
            Retorna a contagem total de likes associados ao vídeo informado.
            O retorno é um mapa no formato {"likes": quantidade}.
        """
    )
    @GetMapping("/{videoPostId}/count")
    public ResponseEntity<Map<String, Long>> getLikeCount(@PathVariable UUID videoPostId) {
        Map<String, Long> response = videoPostLikeService.getLikeFromVideoPost(videoPostId);
        return ResponseEntity.ok(response);
    }
}
