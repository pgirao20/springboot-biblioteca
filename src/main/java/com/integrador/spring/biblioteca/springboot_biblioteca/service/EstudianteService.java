//src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/service/EstudianteService.java
package com.integrador.spring.biblioteca.springboot_biblioteca.service;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Estudiante;
import java.util.List;
import java.util.Optional;

public interface EstudianteService {
    List<Estudiante> listarTodos();
    Optional<Estudiante> buscarPorId(Long id);
    Optional<Estudiante> buscarPorDni(String dni);
    Optional<Estudiante> buscarPorCodigo(String codigo);
    Estudiante guardar(Estudiante estudiante);
    void eliminar(Long id);
}
