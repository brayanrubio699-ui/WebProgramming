package com.example.comics_api.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "Autores")
public class Autor {
    @Id
    private String id;
    private String nombre;
    private String apellido;
    private String nacionalidad;
    private String fechaNacimiento;
    private String biografia;
}