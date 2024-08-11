package com.example.SpringExercise2.controladores.autorizaciones;

import com.example.SpringExercise2.data.usuarios.RoleEnum;
import com.example.SpringExercise2.data.usuarios.UserEntity;
import com.example.SpringExercise2.data.usuarios.UserService;
import com.example.SpringExercise2.excepciones.InvalidCredentialsException;
import com.example.SpringExercise2.seguridadJwt.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_SuccessfulAuthentication_ReturnsToken() {
        // Arrange
        LoginDto loginDto = new LoginDto("test@example.com", "password");
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@example.com");
        userEntity.setContraseña(BCrypt.hashpw("password", BCrypt.gensalt()));
        RoleEnum role = RoleEnum.valueOf("USER");
        List<RoleEnum> roles = Collections.singletonList(role);

        userEntity.setRoles(roles);

        when(userService.buscarPorCorreo(anyString())).thenReturn(Optional.of(userEntity));
        Date expirationDate = new Date(2024 - 1900, 9, 8);  // Año - 1900, Mes (0-based), Día
        when(jwtUtil.generateToken(anyString(), any())).thenReturn(new TokenDto("fake-jwt-token", expirationDate));


        // Act
        ResponseEntity<TokenDto> response = authController.login(loginDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        String token = TokenDto.getToken("fake-jwt-token");
        assertEquals("fake-jwt-token", token);

        verify(userService, times(1)).buscarPorCorreo(anyString());
        verify(jwtUtil, times(1)).generateToken(anyString(), any());
    }

    @Test
    void login_UserNotFound_ThrowsInvalidCredentialsException() {
        // Arrange
        LoginDto loginDto = new LoginDto("notfound@example.com", "password");

        when(userService.buscarPorCorreo(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        InvalidCredentialsException exception = assertThrows(
                InvalidCredentialsException.class,
                () -> authController.login(loginDto)
        );

        assertEquals("Nombre de usuario o contraseña inválida", exception.getMessage());

        verify(userService, times(1)).buscarPorCorreo(anyString());
        verify(jwtUtil, never()).generateToken(anyString(), any());
    }

    @Test
    void login_IncorrectPassword_ThrowsInvalidCredentialsException() {
        // Arrange
        LoginDto loginDto = new LoginDto("test@example.com", "wrongpassword");
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@example.com");
        userEntity.setContraseña(BCrypt.hashpw("correctpassword", BCrypt.gensalt()));

        when(userService.buscarPorCorreo(anyString())).thenReturn(Optional.of(userEntity));

        // Act & Assert
        InvalidCredentialsException exception = assertThrows(
                InvalidCredentialsException.class,
                () -> authController.login(loginDto)
        );

        assertEquals("Nombre de usuario o contraseña inválida", exception.getMessage());

        verify(userService, times(1)).buscarPorCorreo(anyString());
        verify(jwtUtil, never()).generateToken(anyString(), any());
    }
}
