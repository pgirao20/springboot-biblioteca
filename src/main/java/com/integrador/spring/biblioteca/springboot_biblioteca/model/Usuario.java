package com.integrador.spring.biblioteca.springboot_biblioteca.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String usuario;

    @Column(nullable = false, length = 100)
    private String clave;

    @Column(nullable = false, length = 100)
    private String nombre;

    // 🔹 Nuevo campo rol
    @Column(nullable = false, length = 20)
    private String rol; // valores: "ADMIN" o "USER"
}
