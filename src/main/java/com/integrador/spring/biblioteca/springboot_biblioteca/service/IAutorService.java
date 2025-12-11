//src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/service/IAutorService.java
package com.integrador.spring.biblioteca.springboot_biblioteca.service;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Autor;
import java.util.List;
import java.util.Optional;

public interface IAutorService {
    List<Autor> listar();
    Autor guardar(Autor a);
    Optional<Autor> buscar(Long id);
    void eliminar(Long id);
}
