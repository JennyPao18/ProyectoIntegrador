package com.example.SpringExercise2.controladores.autorizaciones;

public class RegisterDto {
    private final String usuario;
    private final String contraseña;

    public RegisterDto(String usuario, String contraseña) {
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }
}
