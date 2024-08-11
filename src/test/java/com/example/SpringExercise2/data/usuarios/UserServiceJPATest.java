package com.example.SpringExercise2.data.usuarios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceJPATest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceJPA userServiceJPA;

    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new UserEntity("John Doe", "john.doe@example.com", "password123");
        testUser.setId("1");
    }

    @Test
    void BuscarPorCorreo() {
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(testUser));

        Optional<UserEntity> result = userServiceJPA.buscarPorCorreo("john.doe@example.com");

        assertTrue(result.isPresent());
        assertEquals(testUser, result.get());
        verify(userRepository, times(1)).findByEmail("john.doe@example.com");
    }

    @Test
    void BuscarPorCorreoInexistente() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        Optional<UserEntity> result = userServiceJPA.buscarPorCorreo("nonexistent@example.com");

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByEmail("nonexistent@example.com");
    }

    @Test
    void BuscarPorId() {
        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));

        Optional<UserEntity> result = userServiceJPA.buscarPorId("1");

        assertTrue(result.isPresent());
        assertEquals(testUser, result.get());
        verify(userRepository, times(1)).findById("1");
    }

    @Test
    void BuscarPorIdInexistente() {
        when(userRepository.findById("999")).thenReturn(Optional.empty());

        Optional<UserEntity> result = userServiceJPA.buscarPorId("999");

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById("999");
    }


    @Test
    void CrearUsuario() {
        when(userRepository.save(testUser)).thenReturn(testUser);

        UserEntity result = userServiceJPA.crearUsuario(testUser);

        assertEquals(testUser, result);
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void CrearUsuarioInvalido() {
        when(userRepository.save(testUser)).thenThrow(new RuntimeException("Error al guardar el usuario"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> userServiceJPA.crearUsuario(testUser));

        assertEquals("Error al guardar el usuario", thrown.getMessage());
        verify(userRepository, times(1)).save(testUser);
    }


    @Test
    void EliminarUsuario() {
        doNothing().when(userRepository).delete(testUser);

        userServiceJPA.eliminarUsuario(testUser);

        verify(userRepository, times(1)).delete(testUser);
    }

    @Test
    void EliminarUsuarioInexistente() {
        doThrow(new RuntimeException("Error al eliminar el usuario")).when(userRepository).delete(testUser);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> userServiceJPA.eliminarUsuario(testUser));

        assertEquals("Error al eliminar el usuario", thrown.getMessage());
        verify(userRepository, times(1)).delete(testUser);
    }

}
