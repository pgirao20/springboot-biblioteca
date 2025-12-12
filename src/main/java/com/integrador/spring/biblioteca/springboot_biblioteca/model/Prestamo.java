//src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/model/Prestamo.java
package com.integrador.spring.biblioteca.springboot_biblioteca.model;
import java.math.BigDecimal;

import jakarta.persistence.Column;
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
   
@Column(name = "estado_devolucion")
private String estadoDevolucion;   

@Column(name = "costo_reparacion")
private BigDecimal costoReparacion;

}

