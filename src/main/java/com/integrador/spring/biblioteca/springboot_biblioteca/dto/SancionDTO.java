// src/main/java/.../dto/SancionDTO.java
package com.integrador.spring.biblioteca.springboot_biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SancionDTO {

    private Long idEstudiante;
    private String dni;
    private String codigo;
    private String nombres;
    private String apellidos;

    private LocalDate inicioSancion;
    private LocalDate finSancion;

    private BigDecimal monto;

    // true = puede prestar, false = no puede
    private boolean disponiblePrestamo;
}
