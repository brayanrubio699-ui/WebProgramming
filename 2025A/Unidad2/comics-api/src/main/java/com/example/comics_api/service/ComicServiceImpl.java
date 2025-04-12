package com.example.comics_api.service;

import com.example.comics_api.dto.AutorDTO;
import com.example.comics_api.dto.ComicDTO;
import com.example.comics_api.model.Autor;
import com.example.comics_api.model.Comic;
import com.example.comics_api.repository.AutorRepository;
import com.example.comics_api.repository.ComicRepository;
import com.example.comics_api.service.IComicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComicServiceImpl implements IComicService {

    @Autowired
    private ComicRepository comicRepository;
    
    @Autowired
    private AutorRepository autorRepository;

    @Override
    public List<ComicDTO> findAllComics() {
        List<Comic> comics = comicRepository.findAll();
        return comics.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ComicDTO> findComicById(String id) {
        return comicRepository.findById(id)
                .map(this::convertToDTO);
    }

    /*
    @Override
    public ComicDTO saveComic(Comic comic) {
        Comic savedComic = comicRepository.save(comic);
        
        // Actualizar la lista de cómics del autor
        if (savedComic.getAutorId() != null) {
            Optional<Autor> autorOpt = autorRepository.findById(savedComic.getAutorId());
            if (autorOpt.isPresent()) {
                Autor autor = autorOpt.get();
                if (autor.getComicsIds() == null) {
                    autor.setComicsIds(new ArrayList<>());
                }
                if (!autor.getComicsIds().contains(savedComic.getId())) {
                    autor.getComicsIds().add(savedComic.getId());
                    autorRepository.save(autor);
                }
            }
        }
        
        return convertToDTO(savedComic);
    }
        */
    

        /*
    @Override
    public void deleteComic(String id) {
        // Primero eliminar la referencia del cómic en el autor
        Optional<Comic> comicOpt = comicRepository.findById(id);
        if (comicOpt.isPresent()) {
            Comic comic = comicOpt.get();
            if (comic.getAutorId() != null) {
                Optional<Autor> autorOpt = autorRepository.findById(comic.getAutorId());
                if (autorOpt.isPresent()) {
                    Autor autor = autorOpt.get();
                    if (autor.getComicsIds() != null) {
                        autor.getComicsIds().remove(id);
                        autorRepository.save(autor);
                    }
                }
            }
        }
        
        // Luego eliminar el cómic
        comicRepository.deleteById(id);
    }
        */

    @Override
    public List<ComicDTO> findComicsByTitulo(String titulo) {
        List<Comic> comics = comicRepository.findByTituloContainingIgnoreCase(titulo);
        return comics.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComicDTO> findComicsByAutorId(String autorId) {
        List<Comic> comics = comicRepository.findByAutorId(autorId);
        return comics.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComicDTO> findComicsByAnioPublicacion(int anioPublicacion) {
        List<Comic> comics = comicRepository.findByAnioPublicacion(anioPublicacion);
        return comics.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComicDTO> findComicsByGenero(String genero) {
        List<Comic> comics = comicRepository.findByGeneros(genero);
        return comics.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ComicDTO convertToDTO(Comic comic) {
        ComicDTO comicDTO = new ComicDTO();
        comicDTO.setId(comic.getId());
        comicDTO.setTitulo(comic.getTitulo());
        comicDTO.setAnioPublicacion(comic.getAnioPublicacion());
        comicDTO.setEditorial(comic.getEditorial());
        comicDTO.setSinopsis(comic.getSinopsis());
        comicDTO.setGeneros(comic.getGeneros());
        
        // Obtener el autor del cómic
        if (comic.getAutorId() != null) {
            Optional<Autor> autorOpt = autorRepository.findById(comic.getAutorId());
            if (autorOpt.isPresent()) {
                Autor autor = autorOpt.get();
                AutorDTO autorDTO = new AutorDTO();
                autorDTO.setId(autor.getId());
                autorDTO.setNombre(autor.getNombre());
                autorDTO.setApellido(autor.getApellido());
                autorDTO.setNacionalidad(autor.getNacionalidad());
                autorDTO.setFechaNacimiento(autor.getFechaNacimiento());
                comicDTO.setAutor(autorDTO);
            }
        }
        
        return comicDTO;
    }
}
