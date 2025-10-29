package com.integrador.spring.biblioteca.springboot_biblioteca.service;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Tablet;
import java.util.List;

public interface TabletService {
    List<Tablet> listarTodos();
    Tablet guardar(Tablet tablet);
    Tablet obtenerPorId(Long id);
    void eliminar(Long id);
}
