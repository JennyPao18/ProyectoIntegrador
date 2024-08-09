package com.example.SpringExercise2.controladores.autorizaciones;

import java.util.Date;

public record TokenDto(String token, Date expirationDate) {
}
