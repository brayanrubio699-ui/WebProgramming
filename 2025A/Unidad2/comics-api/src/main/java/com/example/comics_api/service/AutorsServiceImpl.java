package com.example.comics_api.service;

import com.example.comics_api.dto.AutorDTO;
import com.example.comics_api.dto.ComicDTO;
import com.example.comics_api.model.Autor;
import com.example.comics_api.model.Comic;
import com.example.comics_api.repository.AutorRepository;
import com.example.comics_api.repository.ComicRepository;
import com.example.comics_api.service.IAutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AutorsServiceImpl implements IAutorService {

    @Autowired
    private AutorRepository autorRepository;
    
    @Autowired
    private ComicRepository comicRepository;

    @Override
    public List<AutorDTO> findAllAutores() {
        List<Autor> autores = autorRepository.findAll();
        return autores.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AutorDTO> findAutorById(String id) {
        return autorRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public AutorDTO saveAutor(Autor autor) {
        return convertToDTO(autorRepository.save(autor));
    }

    @Override
    public void deleteAutor(String id) {
        autorRepository.deleteById(id);
    }

    @Override
    public List<AutorDTO> findAutoresByNombre(String nombre) {
        List<Autor> autores = autorRepository.findByNombreContainingIgnoreCase(nombre);
        return autores.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AutorDTO> findAutoresByNacionalidad(String nacionalidad) {
        List<Autor> autores = autorRepository.findByNacionalidad(nacionalidad);
        return autores.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AutorDTO convertToDTO(Autor autor) {
        AutorDTO autorDTO = new AutorDTO();
        autorDTO.setId(autor.getId());
        autorDTO.setNombre(autor.getNombre());
        autorDTO.setApellido(autor.getApellido());
        autorDTO.setNacionalidad(autor.getNacionalidad());
        autorDTO.setFechaNacimiento(autor.getFechaNacimiento());
        autorDTO.setBiografia(autor.getBiografia());
        
        // Obtener los cómics asociados al autor
        /*
        List<ComicDTO> comics = new ArrayList<>();
        if (autor.getComicsIds() != null) {
            comics = autor.getComicsIds().stream()
                    .map(comicId -> comicRepository.findById(comicId))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(comic -> {
                        ComicDTO comicDTO = new ComicDTO();
                        comicDTO.setId(comic.getId());
                        comicDTO.setTitulo(comic.getTitulo());
                        comicDTO.setAnioPublicacion(comic.getAnioPublicacion());
                        comicDTO.setEditorial(comic.getEditorial());
                        comicDTO.setSinopsis(comic.getSinopsis());
                        comicDTO.setGeneros(comic.getGeneros());
                        return comicDTO;
                    })
                    .collect(Collectors.toList());
        }
        autorDTO.setComics(comics);
        */
        
        return autorDTO;
    }

}
