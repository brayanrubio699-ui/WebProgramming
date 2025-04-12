package com.example.comics_api.dto;

import lombok.Data;

@Data
public class AutorDTO {
    private String id;
    private String nombre;
    private String apellido;
    private String nacionalidad;
    private String fechaNacimiento;
    private String biografia;
}
