package com.integrador.spring.biblioteca.springboot_biblioteca.service.impl;

import java.util.List;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Libro;
import com.integrador.spring.biblioteca.springboot_biblioteca.repository.LibroRepository;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.LibroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;

    @Override
    public List<Libro> listarTodos() {
        return libroRepository.findAll();
    }

    @Override
    public Optional<Libro> buscarPorId(Long id) {
        return libroRepository.findById(id);
    }

    @Override
    public Optional<Libro> buscarPorSn(String sn) {
        return libroRepository.findBySn(sn);
    }

    @Override
    public Libro guardar(Libro libro) {
        return libroRepository.save(libro);
    }

    @Override
    public void eliminar(Long id) {
        libroRepository.deleteById(id);
    }

        @Override
    public List<Libro> listarDisponibles() {
        return libroRepository.findByEstado("DISPONIBLE");
    }
}
