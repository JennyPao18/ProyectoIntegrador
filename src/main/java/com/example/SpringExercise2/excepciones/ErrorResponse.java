package com.example.SpringExercise2.excepciones;

import java.time.LocalDateTime;

public class ErrorResponse {
    private final String mensaje;
    private final LocalDateTime timestamp;

    public ErrorResponse(String message) {
        this.mensaje = message;
        this.timestamp = LocalDateTime.now();
    }
}
