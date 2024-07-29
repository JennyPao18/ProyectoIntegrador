package com.example.SpringExercise2.servicios;

import com.example.SpringExercise2.modelos.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserServiceMethods{

    //Lista que contendrá a todos los usuarios
    private Map<Long, User> listaUsuarios = new HashMap<>();
    private long idActual = 1;

    //Método que se utiliza para crear usuarios
    @Override
    public User crearUsuario(User user) {
        user.setId(idActual++);
        listaUsuarios.put(user.getId(), user);
        return user;
    }

    //Método que se utiliza para actualizar usuarios
    @Override
    public User actualizarUsuario(long id, User user) {
        if(listaUsuarios.containsKey(id)){
            user.setId(id);
            listaUsuarios.put(id, user);
            return user;
        }
        return null;
    }

    //Método que se utiliza para eliminar usuarios
    @Override
    public void eliminarUsuario(long id) {
        listaUsuarios.remove(id);
    }

    //Método que se utiliza para buscar usuarios por su id
    @Override
    public User buscarUsuarioPorId(long id) {
        return listaUsuarios.get(id);
    }

    //Método que se utiliza para mostrar toda la lista de usuarios
    @Override
    public List<User> mostrarUsuarios() {
        return new ArrayList<>(listaUsuarios.values());
    }
}
