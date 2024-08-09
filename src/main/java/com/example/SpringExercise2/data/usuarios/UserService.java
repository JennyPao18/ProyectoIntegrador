package com.example.SpringExercise2.data.usuarios;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> buscarPorCorreo(String email);
    Optional<UserEntity> buscarPorId(String id);
    UserEntity crearUsuario(UserEntity user);
    void eliminarUsuario(UserEntity user);
}
