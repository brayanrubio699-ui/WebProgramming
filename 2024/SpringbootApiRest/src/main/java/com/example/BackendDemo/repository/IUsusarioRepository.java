package com.example.BackendDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackendDemo.model.Usuario;

public interface IUsusarioRepository extends JpaRepository<Usuario, Long> {
    
}
