package com.example.comicsapi.service;

import com.example.comicsapi.dto.AutorDTO;
import com.example.comicsapi.model.Autor;
import java.util.List;
import java.util.Optional;

public interface AutorService {
    List<AutorDTO> findAllAutores();
    Optional<AutorDTO> findAutorById(String id);
    AutorDTO saveAutor(Autor autor);
    void deleteAutor(String id);
    List<AutorDTO> findAutoresByNombre(String nombre);
    List<AutorDTO> findAutoresByNacionalidad(String nacionalidad);
}
