package com.example.SpringExercise2.excepciones;

public class UserWithEmailAlreadyRegisteredException extends ServerErrorException {
    public UserWithEmailAlreadyRegisteredException(String emailAlreadyInUse) {
        super("Otro usuario ya esta registrado con este correo");
    }
}
