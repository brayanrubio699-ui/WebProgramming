package com.example.comics_api.controller;

import com.example.comics_api.dto.ComicDTO;
import com.example.comics_api.model.Comic;
import com.example.comics_api.service.IComicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comics")
public class ComicController {

    @Autowired
    private IComicService comicService;

    @GetMapping("/")
    public ResponseEntity<List<ComicDTO>> getAllComics() {
        return ResponseEntity.ok(comicService.findAllComics());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComicDTO> getComicById(@PathVariable String id) {
        return comicService.findComicById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /*
    @PostMapping
    public ResponseEntity<ComicDTO> createComic(@RequestBody Comic comic) {
        return new ResponseEntity<>(comicService.saveComic(comic), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComicDTO> updateComic(@PathVariable String id, @RequestBody Comic comic) {
        return comicService.findComicById(id)
                .map(comicDTO -> {
                    comic.setId(id);
                    return ResponseEntity.ok(comicService.saveComic(comic));
                })
                .orElse(ResponseEntity.notFound().build());
    }
                

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComic(@PathVariable String id) {
        return comicService.findComicById(id)
                .map(comicDTO -> {
                    comicService.deleteComic(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
                */

    
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<ComicDTO>> getComicsByTitulo(@PathVariable String titulo) {
        return ResponseEntity.ok(comicService.findComicsByTitulo(titulo));
    }

    @GetMapping("/autor/{autorId}")
    public ResponseEntity<List<ComicDTO>> getComicsByAutorId(@PathVariable String autorId) {
        return ResponseEntity.ok(comicService.findComicsByAutorId(autorId));
    }

    @GetMapping("/anio/{anio}")
    public ResponseEntity<List<ComicDTO>> getComicsByAnioPublicacion(@PathVariable int anio) {
        return ResponseEntity.ok(comicService.findComicsByAnioPublicacion(anio));
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<ComicDTO>> getComicsByGenero(@PathVariable String genero) {
        return ResponseEntity.ok(comicService.findComicsByGenero(genero));
    }
}