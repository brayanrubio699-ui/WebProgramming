package com.example.BackendDemo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BackendDemo.model.Usuario;
import com.example.BackendDemo.repository.IUsusarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private IUsusarioRepository usuarioRepository;

    //OPERACIONES DEL CRUD
    //Listar todos los usuarios
    public List<Usuario> selectAllUsers()
    {
        return usuarioRepository.findAll();
    }

    //Encontrar solo un usuario:
    public Optional<Usuario> selectById(Long id)
    {
        return usuarioRepository.findById(id);
    }

    //Crear
    public Usuario createUser(Usuario nuevoUsuario)
    {
        return usuarioRepository.save(nuevoUsuario);
    }  

}
