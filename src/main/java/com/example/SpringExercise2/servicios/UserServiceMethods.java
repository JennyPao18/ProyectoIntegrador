package com.example.SpringExercise2.servicios;

import com.example.SpringExercise2.modelos.User;

import java.util.List;

public interface UserServiceMethods {
    //Método para crear usuarios
    User crearUsuario(User user);

    //Método para actualizar usuarios
    User actualizarUsuario (long id, User user);

    //Método para eliminar usuarios
    void eliminarUsuario (long id);

    //Método para buscar usuarios por su Id
    User buscarUsuarioPorId(long id);

    //Método para mostrar un listado con todos los usuarios
    List<User> mostrarUsuarios();
}
