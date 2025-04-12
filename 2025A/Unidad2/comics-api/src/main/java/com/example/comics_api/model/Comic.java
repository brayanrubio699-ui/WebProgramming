package com.example.comics_api.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "Comics")
public class Comic {
    @Id
    private String id;
    private String titulo;
    private int anioPublicacion;
    private String editorial;
    private String sinopsis;
    private List<String> generos;
    private String autorId; // Referencia al autor
}