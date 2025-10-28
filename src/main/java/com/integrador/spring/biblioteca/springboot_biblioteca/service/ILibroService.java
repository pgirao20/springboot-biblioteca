// service/ILibroService.java
package com.integrador.spring.biblioteca.springboot_biblioteca.service;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Libro;
import java.util.List;
import java.util.Optional;

public interface ILibroService {
    List<Libro> listar();
    Libro guardar(Libro l);
    Optional<Libro> buscar(Long id);
    void eliminar(Long id);
}
