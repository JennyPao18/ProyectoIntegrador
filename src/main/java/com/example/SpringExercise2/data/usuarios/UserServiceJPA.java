package com.example.SpringExercise2.data.usuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceJPA implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceJPA(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserEntity> buscarPorCorreo(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> buscarPorId(String id) {
        return userRepository.findById(id);
    }

    @Override
    public UserEntity crearUsuario(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public void eliminarUsuario(UserEntity user) {
        userRepository.delete(user);
    }
}

