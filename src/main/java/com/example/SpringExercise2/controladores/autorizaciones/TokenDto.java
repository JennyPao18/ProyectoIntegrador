package com.example.SpringExercise2.controladores.autorizaciones;

import ch.qos.logback.core.subst.Token;

public record TokenDto(String token, java.util.Date expirationDate) {

    public static String getToken(String Token) {
        return Token;
    }
}
