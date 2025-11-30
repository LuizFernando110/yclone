package com.example.yclone.controller;

import com.example.yclone.dto.user.CreateUserDTO;
import com.example.yclone.dto.user.UpdateUserDTO;
import com.example.yclone.dto.user.UserDTO;
import com.example.yclone.dto.user.UserDetailDTO;
import com.example.yclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserDetailDTO getById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public UserDTO createUser(@RequestBody CreateUserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PutMapping("/{id}")
    public UserDetailDTO updateUser(@PathVariable UUID id,
                                    @RequestBody UpdateUserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}
