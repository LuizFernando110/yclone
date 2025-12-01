package com.example.yclone.controller;

import com.example.yclone.dto.ViewDTO;
import com.example.yclone.models.View;
import com.example.yclone.service.ViewService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/views")
public class ViewController {

    @Autowired
    private ViewService viewService;

    @Operation(
            summary = "Obtém a quantidade de visualizações de um vídeo",
            description = """
            Retorna a contagem total de visualizações registradas para um vídeo específico.
            O retorno é um mapa no formato {"views": quantidade}.
        """
    )
    @GetMapping("/{videoPostId}/count")
    public ResponseEntity<Map<String, Long>> getViewCount(@PathVariable UUID videoPostId) {
        Map<String, Long> response = viewService.getViewsFromVideoPost(videoPostId);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Registra uma visualização",
            description = """
            Cria um novo registro de visualização para um vídeo, associado a um usuário.
            Retorna o ID da visualização criada.
        """
    )
    @PostMapping
    public ResponseEntity<?> registerView(@RequestBody ViewDTO dto) {
        View view = viewService.registerView(dto.getUserId(), dto.getVideoId());
        return ResponseEntity.ok(view.getId());
    }

    @Operation(
            summary = "Remove uma visualização",
            description = """
            Exclui uma visualização existente usando o ID da visualização.
            Retorna HTTP 204 quando removida com sucesso.
        """
    )
    @DeleteMapping("/{viewId}")
    public ResponseEntity<?> deleteView(@PathVariable UUID viewId) {
        viewService.deleteView(viewId);
        return ResponseEntity.noContent().build();
    }
}
