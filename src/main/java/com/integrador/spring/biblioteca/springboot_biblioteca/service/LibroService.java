package com.integrador.spring.biblioteca.springboot_biblioteca.service;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Libro;

import java.util.List;
import java.util.Optional;

public interface LibroService {
    List<Libro> listarTodos();
    Optional<Libro> buscarPorId(Long id);
    Optional<Libro> buscarPorSn(String sn);
    Libro guardar(Libro libro);
    void eliminar(Long id);
}
