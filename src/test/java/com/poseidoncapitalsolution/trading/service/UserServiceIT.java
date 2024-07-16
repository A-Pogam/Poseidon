package com.poseidoncapitalsolution.trading.service;

import com.poseidoncapitalsolution.trading.model.User;
import com.poseidoncapitalsolution.trading.repository.contracts.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceIT {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("testUser");
        user.setPassword("password");
    }

    @Test
    public void testFindByUsername() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername("testUser");

        assertTrue(result.isPresent());
        assertEquals(user.getUsername(), result.get().getUsername());
        verify(userRepository, times(1)).findByUsername(anyString());
    }



    @Test
    public void testGetAllUsers() {
        List<User> users = Arrays.asList(user, new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(1);

        assertTrue(result.isPresent());
        assertEquals(user.getId(), result.get().getId());
        verify(userRepository, times(1)).findById(anyInt());
    }

    @Test
    public void testFindById_NotFound() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        Optional<User> result = userService.findById(1);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(anyInt());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(userRepository).deleteById(anyInt());

        userService.deleteById(1);

        verify(userRepository, times(1)).deleteById(anyInt());
    }
}
