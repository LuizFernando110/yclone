package com.example.yclone.controller;

import com.example.yclone.dto.user.CreateUserDTO;
import com.example.yclone.dto.user.UpdateUserDTO;
import com.example.yclone.dto.user.UserDTO;
import com.example.yclone.dto.user.UserDetailDTO;
import com.example.yclone.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "Gerenciamento de usuários do sistema")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Busca usuário pelo ID",
            description = "Retorna detalhes completos de um usuário com base no seu identificador único."
    )
    @GetMapping("/{id}")
    public UserDetailDTO getById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @Operation(
            summary = "Cria um novo usuário",
            description = "Cria um usuário no sistema com as informações fornecidas no corpo da requisição."
    )
    @PostMapping
    public UserDTO createUser(@RequestBody CreateUserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @Operation(
            summary = "Atualiza dados de um usuário",
            description = "Atualiza as informações de um usuário já existente utilizando seu ID."
    )
    @PutMapping("/{id}")
    public UserDetailDTO updateUser(
            @PathVariable UUID id,
            @RequestBody UpdateUserDTO userDTO
    ) {
        return userService.updateUser(id, userDTO);
    }

    @Operation(
            summary = "Remove um usuário",
            description = "Deleta permanentemente um usuário com base em seu ID."
    )
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}
