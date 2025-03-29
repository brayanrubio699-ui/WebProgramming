package com.example.comicsapi.dto;

import lombok.Data;
import java.util.List;

@Data
public class AutorDTO {
    private String id;
    private String nombre;
    private String apellido;
    private String nacionalidad;
    private String fechaNacimiento;
    private String biografia;
    private List<ComicDTO> comics; // Relación con cómics
}
