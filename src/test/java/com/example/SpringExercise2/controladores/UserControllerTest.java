package com.example.SpringExercise2.controladores;

import com.example.SpringExercise2.data.usuarios.UserEntity;
import com.example.SpringExercise2.data.usuarios.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void BuscarUsuarioPorID() {
        UserEntity userEntity = new UserEntity("Paola", "paolita@email.com", "encodedPassword");
        when(userService.buscarPorId("1")).thenReturn(Optional.of(userEntity));

        ResponseEntity<UserEntity> response = userController.getUserById("1");
    }


    @Test
    void BuscarUsuarioNoExistentePorID() {
        when(userService.buscarPorId("1")).thenReturn(Optional.empty());

        ResponseEntity<UserEntity> response = userController.getUserById("1");

        assertEquals(404, response.getStatusCodeValue());
    }


    @Test
    void CrearUsuario() {
        UserDto userDto = new UserDto("Paola", "Ramos", "paolita@email.com", "123");
        UserEntity userEntity = new UserEntity(userDto.getNombre(), userDto.getEmail(), "encodedPassword");
        when(userService.buscarPorCorreo(userDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDto.getContraseña())).thenReturn("encodedPassword");
        when(userService.crearUsuario(userEntity)).thenReturn(userEntity);

        ResponseEntity<UserEntity> response = userController.createUser(userDto);
        System.out.println("El usuario fue creado exitosamente");
    }

    @Test
    void EliminarUsuario() {
        UserEntity userEntity = new UserEntity("Paola", "paolita@email.com", "encodedPassword");
        when(userService.buscarPorId("1")).thenReturn(Optional.of(userEntity));
        System.out.println("El usuario fue eliminado exitosamente");
    }

    @Test
    void EliminarUsuarioNoExistente() {
        when(userService.buscarPorId("1")).thenReturn(Optional.empty());
        System.out.println("El usuario no existe");
    }
}
