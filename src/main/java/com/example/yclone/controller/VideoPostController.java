package com.example.yclone.controller;

import com.example.yclone.dto.videoPost.CreateVideoPostDTO;
import com.example.yclone.dto.videoPost.UpdateVideoPostDTO;
import com.example.yclone.dto.videoPost.VideoPostDTO;
import com.example.yclone.dto.videoPost.VideoPostDetailDTO;
import com.example.yclone.models.User;
import com.example.yclone.service.VideoPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/video-post")
@Tag(name = "Video Posts", description = "Gerenciamento de vídeos postados")
public class VideoPostController {

    @Autowired
    private VideoPostService videoPostService;

    @Operation(
            summary = "Lista todos os vídeos",
            description = "Retorna uma lista com todos os vídeos cadastrados no sistema."
    )
    @GetMapping
    public List<VideoPostDTO> list() {
        return videoPostService.findAll();
    }

    @Operation(
            summary = "Busca vídeo pelo ID",
            description = "Retorna os detalhes completos de um vídeo específico informado pelo ID."
    )
    @GetMapping("/{id}")
    public VideoPostDetailDTO getById(@PathVariable UUID id){
        return videoPostService.getVideoPostById(id);
    }

    @Operation(
            summary = "Cria um novo vídeo",
            description = "Recebe os dados de criação de um vídeo e retorna o registro criado."
    )
    @PostMapping
    public VideoPostDTO create(
            @RequestBody CreateVideoPostDTO dto,
            @AuthenticationPrincipal User user
    ){
        return videoPostService.createVideoPost(dto, user);
    }

    @Operation(
            summary = "Atualiza um vídeo",
            description = "Atualiza os dados de um vídeo existente, identificado pelo ID."
    )
    @PutMapping("/{id}")
    public VideoPostDetailDTO update(
            @PathVariable UUID id,
            @RequestBody UpdateVideoPostDTO dto
    ){
        return videoPostService.updateVideoPost(id, dto);
    }

    @Operation(
            summary = "Deleta um vídeo",
            description = "Remove um vídeo do sistema identificando-o pelo ID."
    )
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        videoPostService.deleteVideoPost(id);
    }
}
