package com.integrador.spring.biblioteca.springboot_biblioteca.service;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.MarcaTablet;

import java.util.List;
import java.util.Optional;

public interface IMarcaTabletService {
    List<MarcaTablet> listarTodos();
    MarcaTablet guardar(MarcaTablet marca);
    Optional<MarcaTablet> buscarPorId(Long id);
    void eliminar(Long id);
    boolean existePorNombre(String nombre);
}
