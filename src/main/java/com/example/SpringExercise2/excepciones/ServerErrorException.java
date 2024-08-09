package com.example.SpringExercise2.excepciones;

public abstract class ServerErrorException extends RuntimeException {

    public ServerErrorException(String mensaje) {
        super(mensaje);
    }
}
