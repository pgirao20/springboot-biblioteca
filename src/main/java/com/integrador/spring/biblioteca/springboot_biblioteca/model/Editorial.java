package com.integrador.spring.biblioteca.springboot_biblioteca.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity @Table(name = "editoriales")
@Data
public class Editorial {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 100)
    private String nombre;
}
