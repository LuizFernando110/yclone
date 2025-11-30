package com.example.yclone.service;

import com.example.yclone.dto.user.CreateUserDTO;
import com.example.yclone.dto.user.UpdateUserDTO;
import com.example.yclone.dto.user.UserDTO;
import com.example.yclone.dto.user.UserDetailDTO;
import com.example.yclone.models.User;
import com.example.yclone.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Create
    public UserDTO createUser(CreateUserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByUserName(dto.getUserName())) {
            throw new RuntimeException("Username already exists");
        }

        User user = modelMapper.map(dto, User.class);

        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserDTO.class);
    }

    // Read
    public UserDetailDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(user, UserDetailDTO.class);
    }

    // Update
    public UserDetailDTO updateUser(UUID id, UpdateUserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (dto.getUserName() != null && !dto.getUserName().isBlank()) {
            if (userRepository.existsByUserName(dto.getUserName()) &&
                    !user.getUserName().equals(dto.getUserName())) {
                throw new RuntimeException("Username already exists");
            }
            user.setUserName(dto.getUserName());
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            if (userRepository.existsByEmail(dto.getEmail()) &&
                    !user.getEmail().equals(dto.getEmail())) {
                throw new RuntimeException("Email already exists");
            }
            user.setEmail(dto.getEmail());
        }

        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDetailDTO.class);
    }

    // Delete
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
}
