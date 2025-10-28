package com.integrador.spring.biblioteca.springboot_biblioteca.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity @Table(name = "libros")
@Data
public class Libro {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    // Guardamos el nombre tal como lo usa el template actual
    @Column(nullable = false, length = 100)
    private String autor;

    @Column(nullable = false, length = 100)
    private String editorial;

    @Column(nullable = false, length = 100)
    private String area;

    @Column(nullable = false)
    private Integer anio;
       
    @Column(nullable = false)
    private Integer stock;
}
