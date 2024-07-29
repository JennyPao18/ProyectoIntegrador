package com.example.SpringExercise2.modelos;

public class User {
    //Atributos de los usuarios
    private long id;
    private String nombre;
    private String email;

    //Constructor de la clase con parámetros
    public User(long id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    //*****************************************************************************
    //Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
