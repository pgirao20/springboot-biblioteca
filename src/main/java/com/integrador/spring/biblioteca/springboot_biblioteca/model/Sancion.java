//src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/model/Sancion.java
package com.integrador.spring.biblioteca.springboot_biblioteca.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sancion {
    private String codigo;
    private String codigoEstudiante;
    private String estudiante;
    private String motivo;
    private String fechaInicio;
    private String fechaFin;
    private double monto;
    private String estado;
}
