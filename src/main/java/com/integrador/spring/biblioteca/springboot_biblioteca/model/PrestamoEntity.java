// src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/model/PrestamoEntity.java
package com.integrador.spring.biblioteca.springboot_biblioteca.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "prestamos")
@Data
public class PrestamoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ACTIVO, PENDIENTE, VENCIDO, DEVUELTO
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoPrestamo estado;

    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo;

    @Column(name = "fecha_devolucion", nullable = false)
    private LocalDate fechaDevolucion;

    // opcional: fecha real de devolución
    @Column(name = "fecha_devolucion_real")
    private LocalDate fechaDevolucionReal;

    // AQUÍ EL CAMPO QUE FALTABA
    @Column(name = "costo_reparacion", precision = 10, scale = 2)
    private BigDecimal costoReparacion;

    @ManyToOne
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;

    @ManyToOne
    @JoinColumn(name = "tablet_id")
    private Tablet tablet;   // para libros será null
}
