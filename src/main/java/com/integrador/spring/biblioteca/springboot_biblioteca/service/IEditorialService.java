// service/IEditorialService.java
package com.integrador.spring.biblioteca.springboot_biblioteca.service;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Editorial;
import java.util.List;
import java.util.Optional;

public interface IEditorialService {
    List<Editorial> listar();
    Editorial guardar(Editorial e);
    Optional<Editorial> buscar(Long id);
    void eliminar(Long id);
}
