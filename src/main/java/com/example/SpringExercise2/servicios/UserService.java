package com.example.SpringExercise2.servicios;

import com.example.SpringExercise2.entidades.User;
import com.example.SpringExercise2.repositorios.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService{
    @Autowired
    private UserRepository userRepository;

    /*//Lista que contendrá a todos los usuarios
    private Map<Long, User> listaUsuarios = new HashMap<>();
    private long idActual = 1;*/

    //Método que se utiliza para crear usuarios
    public User crearUsuario(User user) {
        return userRepository.save(user);
    }

    //Método que se utiliza para actualizar usuarios
    public User actualizarUsuario(String id, User user) {
        if(userRepository.existsById(id)){
            user.setId(id);
            return userRepository.save(user);
        }
        return null;
    }

    //Método que se utiliza para eliminar usuarios
    public void eliminarUsuario(String id) {
        userRepository.deleteById(id);
    }

    //Método que se utiliza para buscar usuarios por su id
    public User buscarUsuarioPorId(String id) {
        Optional<User> usuarioABuscar = userRepository.findById(id);
        return usuarioABuscar.orElse(null);
    }

    //Método que se utiliza para mostrar toda la lista de usuarios
    public List<User> mostrarUsuarios() {
        return userRepository.findAll();
    }

}
