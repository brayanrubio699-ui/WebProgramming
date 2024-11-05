package com.example.BackendDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackendDemo.model.Usuario;
import com.example.BackendDemo.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //Endpoint para listar todos los usuarios:
    //(Usa método HTTP Get)
    @GetMapping
    public List<Usuario> listarUsuarios()
    {
        return usuarioService.selectAllUsers();
    }


    //Endpoint para guardar un usuario:
    //(Usa método HTTP Post)
    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario nuevoUsuario)
    {
        return usuarioService.createUser(nuevoUsuario);
    }


}
