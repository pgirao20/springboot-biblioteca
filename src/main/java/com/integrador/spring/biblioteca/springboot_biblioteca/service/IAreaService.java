// service/IAreaService.java
package com.integrador.spring.biblioteca.springboot_biblioteca.service;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Area;
import java.util.List;
import java.util.Optional;

public interface IAreaService {
    List<Area> listar();
    Area guardar(Area a);
    Optional<Area> buscar(Long id);
    void eliminar(Long id);
}
