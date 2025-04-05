package com.example.comics_api.service;

import com.example.comics_api.dto.AutorDTO;
import com.example.comics_api.model.Autor;
import java.util.List;
import java.util.Optional;

public interface IAutorService {
    List<AutorDTO> findAllAutores();
    Optional<AutorDTO> findAutorById(String id);
    AutorDTO saveAutor(Autor autor);
    void deleteAutor(String id);
    List<AutorDTO> findAutoresByNombre(String nombre);
    List<AutorDTO> findAutoresByNacionalidad(String nacionalidad);
}
