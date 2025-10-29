package com.integrador.spring.biblioteca.springboot_biblioteca.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tablets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tablet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con MarcaTablet
    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    private MarcaTablet marca;

    @Column(nullable = false, length = 100)
    private String modelo;

    @Column(nullable = false, unique = true, length = 100)
    private String sn;

    @Column(name = "anio_adquisicion", nullable = false)
    private Integer anioAdquisicion;

    @Builder.Default
    @Column(nullable = false, length = 30)
    private String estado = "Disponible";
}
