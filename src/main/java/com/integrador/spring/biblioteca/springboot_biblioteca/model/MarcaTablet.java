package com.integrador.spring.biblioteca.springboot_biblioteca.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "marca_tablet", uniqueConstraints = {
        @UniqueConstraint(name = "uk_marca_nombre", columnNames = {"nombre"})
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MarcaTablet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(length = 120)
    private String descripcion;
}
