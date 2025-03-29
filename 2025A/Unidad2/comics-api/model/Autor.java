package com.example.comicsapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "autores")
public class Autor {
    @Id
    private String id;
    private String nombre;
    private String apellido;
    private String nacionalidad;
    private String fechaNacimiento;
    private String biografia;
    private List<String> comicsIds; // Referencias a los cómics del autor
}
