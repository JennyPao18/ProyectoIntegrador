package com.example.SpringExercise2.excepciones;

public class InvalidCredentialsException extends ServerErrorException {

    public InvalidCredentialsException(String invalidUsernameOrPassword) {
        super("Nombre de usuario o contraseña inválida");
    }
}
