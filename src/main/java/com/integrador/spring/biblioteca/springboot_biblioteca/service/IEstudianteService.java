package com.integrador.spring.biblioteca.springboot_biblioteca.service;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Estudiante;
import java.util.List;
import java.util.Optional;

public interface IEstudianteService {
    List<Estudiante> listar();
    void guardar(Estudiante estudiante);
    Optional<Estudiante> buscarPorId(Long id);
    void eliminar(Long id);
}
