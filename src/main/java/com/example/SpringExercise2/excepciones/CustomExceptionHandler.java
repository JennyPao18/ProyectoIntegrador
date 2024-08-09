package com.example.SpringExercise2.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerErrorException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(ServerErrorException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ex.printStackTrace();
        String errorMessage = "Ha ocurrido un error";
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof ServerErrorException) {
            return handleCustomException((ServerErrorException) ex);
        } else if (ex instanceof AccessDeniedException) {
            errorMessage = "El usuario no puede realizar esa acción";
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<>(new ErrorResponse(errorMessage), httpStatus);
    }
}