//src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/model/Estudiante.java
package com.integrador.spring.biblioteca.springboot_biblioteca.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "estudiantes")
@Data
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 8)
    private String dni;

    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(length = 15)
    private String telefono;

    @Column(length = 100)
    private String correo;

    @Column(nullable = false, length = 20)
    private String grado;

    @Column(nullable = false, length = 5)
    private String seccion;
}
