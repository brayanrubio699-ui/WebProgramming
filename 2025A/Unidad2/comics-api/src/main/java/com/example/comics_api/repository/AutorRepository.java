package com.example.comics_api.repository;

import com.example.comics_api.model.Autor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AutorRepository extends MongoRepository<Autor, String> {
    List<Autor> findByNombreContainingIgnoreCase(String nombre);
    List<Autor> findByNacionalidad(String nacionalidad);
}
