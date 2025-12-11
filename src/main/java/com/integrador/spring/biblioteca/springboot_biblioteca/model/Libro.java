//src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/model/Libro.java
package com.integrador.spring.biblioteca.springboot_biblioteca.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "libros")
@Data
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "editorial_id", nullable = false)
    private Editorial editorial;

    @ManyToOne
    @JoinColumn(name = "area_id", nullable = false)
    private Area area;

    @Column(nullable = false)
    private Integer anio;

    @Column(nullable = false, unique = true, length = 50)
    private String sn;
<<<<<<< HEAD
    
    @Column(nullable = false, length = 20)
    private String estado;   // "DISPONIBLE" o "PRESTADO"
=======

    @Column(nullable = false, length = 20)
    private String estado = "DISPONIBLE";

    @PrePersist
    public void prePersist() {
        if (estado == null || estado.isBlank()) {
            estado = "DISPONIBLE";
        }
    }
>>>>>>> 8a693b5 (prestamos v2)
}

