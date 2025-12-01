package com.example.yclone.controller;

import com.example.yclone.dto.video.CreateVideoDTO;
import com.example.yclone.dto.video.UpdateVideoDTO;
import com.example.yclone.dto.video.VideoDTO;
import com.example.yclone.dto.video.VideoDetailDTO;
import com.example.yclone.models.User;
import com.example.yclone.models.Video;
import com.example.yclone.repository.UserRepository;
import com.example.yclone.service.MediaService;
import com.example.yclone.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
@Tag(name = "Videos", description = "Gerenciamento de vídeos dos usuários")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MediaService mediaService;

    @Operation(
            summary = "Lista vídeos por usuário",
            description = "Retorna todos os vídeos pertencentes a um usuário específico."
    )
    @GetMapping("user/{userId}")
    public List<VideoDTO> list(@PathVariable UUID userId) {
        return videoService.findByUserId(userId);
    }

    @Operation(
            summary = "Busca vídeo pelo ID",
            description = "Retorna os detalhes completos de um vídeo específico."
    )
    @GetMapping("/{videoId}")
    public VideoDetailDTO getById(@PathVariable UUID videoId){
        return videoService.getVideoById(videoId);
    }

    @Operation(
            summary = "Cria um novo vídeo",
            description = "Cria um vídeo apenas com os metadados informados. O arquivo de mídia não é enviado aqui."
    )
    @PostMapping
    public VideoDTO create(@RequestBody CreateVideoDTO dto){
        return  videoService.createVideo(dto);
    }

    @Operation(
            summary = "Atualiza um vídeo existente",
            description = "Atualiza os metadados de um vídeo informado pelo ID."
    )
    @PutMapping("/{videoId}")
    public VideoDTO update(
            @PathVariable UUID videoId,
            @RequestBody UpdateVideoDTO dto
    ){
        return videoService.updateVideo(videoId, dto);
    }

    @Operation(
            summary = "Faz upload de um arquivo de vídeo",
            description = """
                    Recebe um arquivo de vídeo e o armazena no provedor configurado (como MinIO).
                    Retorna os detalhes completos do vídeo enviado.
                    """
    )
    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<VideoDetailDTO> uploadVideo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") UUID userId
    ) throws Exception {

        if(file.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "File cannot be empty"
            );
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"
                ));

        Video video = mediaService.uploadVideo(file, user);
        VideoDetailDTO dto = modelMapper.map(video, VideoDetailDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Operation(
            summary = "Deleta um vídeo",
            description = "Remove um vídeo do sistema permanentemente."
    )
    @DeleteMapping("/{videoId}")
    public void delete(@PathVariable UUID videoId){
        videoService.deleteVideo(videoId);
    }
}
