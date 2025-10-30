package com.integrador.spring.biblioteca.springboot_biblioteca.service;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Tablet;
import java.util.List;
import java.util.Optional;

public interface TabletService {
    List<Tablet> listarTodos();
    Optional<Tablet> buscarPorId(Long id);
    Optional<Tablet> buscarPorSn(String sn);
    Tablet guardar(Tablet tablet);
    void eliminar(Long id);
}
