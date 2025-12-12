// src/main/java/.../service/SancionService.java
package com.integrador.spring.biblioteca.springboot_biblioteca.service;

import com.integrador.spring.biblioteca.springboot_biblioteca.dto.SancionDTO;

import java.util.List;

public interface SancionService {

    List<SancionDTO> listarSanciones();

    void quitarSancion(Long idEstudiante);

    void borrarSancion(Long idEstudiante);
}
