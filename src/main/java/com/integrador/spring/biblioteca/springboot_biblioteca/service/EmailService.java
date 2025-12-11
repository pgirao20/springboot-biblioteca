
//src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/service/EmailService.java
package com.integrador.spring.biblioteca.springboot_biblioteca.service;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Estudiante;

import java.time.LocalDate;
import java.util.List;

public interface EmailService {

    void enviarCorreoPrestamo(Estudiante estudiante,
                              String tipo, // "LIBRO" o "TABLET"
                              LocalDate fechaPrestamo,
                              LocalDate fechaDevolucion,
                              List<String> items); // títulos o "SN - Modelo"
}

