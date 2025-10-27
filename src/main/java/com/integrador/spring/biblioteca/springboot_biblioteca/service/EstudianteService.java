package com.integrador.spring.biblioteca.springboot_biblioteca.service;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Estudiante;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {

    private final List<Estudiante> estudiantes = new ArrayList<>();

    public EstudianteService() {
        // Datos simulados iniciales
        estudiantes.add(new Estudiante(1L, "72845123", "2024001", "María", "López", "987456321",
                "maria.lopez@colegio.edu", "5to Sec", "A"));
        estudiantes.add(new Estudiante(2L, "74982345", "2024002", "Carlos", "Pérez", "956321478",
                "carlos.perez@colegio.edu", "4to Sec", "B"));
    }

    public List<Estudiante> listar() {
        return estudiantes;
    }

    public void guardar(Estudiante e) {
        if (e.getId() == null) {
            e.setId((long) (estudiantes.size() + 1));
            estudiantes.add(e);
        } else {
            eliminar(e.getId());
            estudiantes.add(e);
        }
    }

    public void eliminar(Long id) {
        estudiantes.removeIf(e -> e.getId().equals(id));
    }

    public Optional<Estudiante> buscarPorId(Long id) {
        return estudiantes.stream().filter(e -> e.getId().equals(id)).findFirst();
    }
}
