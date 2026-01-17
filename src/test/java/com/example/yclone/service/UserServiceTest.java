package com.example.yclone.service;

import com.example.yclone.dto.user.CreateUserDTO;
import com.example.yclone.dto.user.UpdateUserDTO;
import com.example.yclone.dto.user.UserDTO;
import com.example.yclone.dto.user.UserDetailDTO;
import com.example.yclone.models.User;
import com.example.yclone.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    private User user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        user = new User();
        user.setId(userId);
        user.setUserName("teste");
        user.setEmail("teste@example.com");
    }

    @Test
    void testCreateUserSuccess() {
        CreateUserDTO dto = new CreateUserDTO();
        dto.setUserName("teste");
        dto.setEmail("teste@example.com");

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(userRepository.existsByUserName(dto.getUserName())).thenReturn(false);
        when(modelMapper.map(dto, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserDTO.class)).thenReturn(new UserDTO());

        UserDTO result = userService.createUser(dto);
        assertNotNull(result);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testCreateUserEmailExists() {
        CreateUserDTO dto = new CreateUserDTO();
        dto.setEmail("teste@example.com");

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.createUser(dto));
        assertEquals("Email already exists", ex.getMessage());
    }

    @Test
    void testGetUserByIdSuccess() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDetailDTO.class)).thenReturn(new UserDetailDTO());

        UserDetailDTO result = userService.getUserById(userId);
        assertNotNull(result);
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.getUserById(userId));
        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void testUpdateUserSuccess() {
        UpdateUserDTO dto = new UpdateUserDTO();
        dto.setUserName("novoNome");
        dto.setEmail("novo@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.existsByUserName(dto.getUserName())).thenReturn(false);
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserDetailDTO.class)).thenReturn(new UserDetailDTO());

        UserDetailDTO result = userService.updateUser(userId, dto);
        assertNotNull(result);
        assertEquals("novoNome", user.getUsername());
        assertEquals("novo@example.com", user.getEmail());
    }

    @Test
    void testDeleteUserSuccess() {
        when(userRepository.existsById(userId)).thenReturn(true);
        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUserNotFound() {
        when(userRepository.existsById(userId)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.deleteUser(userId));
        assertEquals("User not found", ex.getMessage());
    }
}
