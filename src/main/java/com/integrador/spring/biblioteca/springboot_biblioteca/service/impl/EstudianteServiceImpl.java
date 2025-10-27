package com.integrador.spring.biblioteca.springboot_biblioteca.service.impl;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Estudiante;
import com.integrador.spring.biblioteca.springboot_biblioteca.repository.EstudianteRepository;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.IEstudianteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteServiceImpl implements IEstudianteService {

    private final EstudianteRepository repository;

    public EstudianteServiceImpl(EstudianteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Estudiante> listar() {
        return repository.findAll();
    }

    @Override
    public void guardar(Estudiante estudiante) {
        repository.save(estudiante);
    }

    @Override
    public Optional<Estudiante> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
