package com.example.comics_api.dto;

import lombok.Data;
import java.util.List;

@Data
public class ComicDTO {
    private String id;
    private String titulo;
    private int anioPublicacion;
    private String editorial;
    private String sinopsis;
    private List<String> generos;
    private AutorDTO autor; // Relación con el autor
}
