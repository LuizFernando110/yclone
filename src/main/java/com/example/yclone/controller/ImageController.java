package com.example.yclone.controller;

import com.example.yclone.dto.image.CreateImageDTO;
import com.example.yclone.dto.image.ImageDTO;
import com.example.yclone.dto.image.ImageDetailDTO;
import com.example.yclone.dto.image.UpdateImageDTO;
import com.example.yclone.models.Image;
import com.example.yclone.models.User;
import com.example.yclone.repository.UserRepository;
import com.example.yclone.service.ImageService;
import com.example.yclone.service.MediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
@Tag(name = "Image", description = "Gerenciamento de imagens enviadas pelos usuários")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MediaService mediaService;

    @Operation(
            summary = "Lista imagens de um usuário",
            description = "Retorna todas as imagens associadas ao usuário especificado pelo ID."
    )
    @GetMapping("user/{userId}")
    public List<ImageDTO> list(@PathVariable UUID userId) {
        return imageService.findByUserId(userId);
    }

    @Operation(
            summary = "Busca imagem pelo ID",
            description = "Retorna os detalhes completos de uma imagem específica."
    )
    @GetMapping("/{imageId}")
    public ImageDetailDTO getById(@PathVariable UUID imageId) {
        return imageService.getImageDetail(imageId);
    }

    @Operation(
            summary = "Cria uma nova imagem",
            description = "Registra uma imagem no sistema usando dados fornecidos no corpo da requisição. Não realiza upload do arquivo."
    )
    @PostMapping
    public ImageDTO create(@RequestBody CreateImageDTO dto) {
        return imageService.createImage(dto);
    }

    @Operation(
            summary = "Faz upload de uma imagem",
            description = "Envia um arquivo de imagem, armazena no servidor e cria o registro correspondente no banco."
    )
    @PostMapping("/upload")
    public ResponseEntity<ImageDetailDTO> uploadImage(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal User user
    ) throws Exception {

        if (file.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "File cannot be empty"
            );
        }

        Image image = mediaService.uploadImage(file, user);
        ImageDetailDTO dto = modelMapper.map(image, ImageDetailDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Operation(
            summary = "Atualiza uma imagem",
            description = "Atualiza os dados de uma imagem já existente, sem alterar o arquivo enviado."
    )
    @PutMapping("/{imageId}")
    public ImageDTO update(@PathVariable UUID imageId,
                           @RequestBody UpdateImageDTO dto) {
        return imageService.updateImage(imageId, dto);
    }

    @Operation(
            summary = "Remove uma imagem",
            description = "Deleta permanentemente uma imagem com base no seu ID."
    )
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        imageService.deleteImage(id);
    }
}
