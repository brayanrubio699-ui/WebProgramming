package com.example.comicsapi.repository;

import com.example.comicsapi.model.Comic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComicRepository extends MongoRepository<Comic, String> {
    List<Comic> findByTituloContainingIgnoreCase(String titulo);
    List<Comic> findByAutorId(String autorId);
    List<Comic> findByAnioPublicacion(int anioPublicacion);
    List<Comic> findByGeneros(String genero);
}
