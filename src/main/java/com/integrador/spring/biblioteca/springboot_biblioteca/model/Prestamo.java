package com.integrador.spring.biblioteca.springboot_biblioteca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo {
    private String codigo;
    private String estudiante;
    private String codigoEstudiante;
    private String item;
    private String fechaPrestamo;
    private String fechaDevolucion;
    private String estado;
}
