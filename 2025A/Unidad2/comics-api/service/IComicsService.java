package com.example.comics_api.service;

import com.example.comics_api.dto.ComicDTO;
import com.example.comics_api.model.Comic;
import java.util.List;
import java.util.Optional;

public interface IComicService {
    List<ComicDTO> findAllComics();
    Optional<ComicDTO> findComicById(String id);
    ComicDTO saveComic(Comic comic);
    void deleteComic(String id);
    List<ComicDTO> findComicsByTitulo(String titulo);
    List<ComicDTO> findComicsByAutorId(String autorId);
    List<ComicDTO> findComicsByAnioPublicacion(int anioPublicacion);
    List<ComicDTO> findComicsByGenero(String genero);
}
