package com.example.auth_demo.model;

@Document(collection=users)
public class User {

    @Id
    private String id;
    private String nombre;
    private String email;

    public User(String nombre, String email)
    {
        this.nombre = nombre;
        this.email = email;

    }
    
}
