// service/impl/LibroServiceImpl.java
package com.integrador.spring.biblioteca.springboot_biblioteca.service.impl;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Libro;
import com.integrador.spring.biblioteca.springboot_biblioteca.repository.LibroRepository;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.ILibroService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroServiceImpl implements ILibroService {
    private final LibroRepository repo;
    public LibroServiceImpl(LibroRepository repo) { this.repo = repo; }
    public List<Libro> listar() { return repo.findAll(); }
    public Libro guardar(Libro l) { return repo.save(l); }
    public Optional<Libro> buscar(Long id) { return repo.findById(id); }
    public void eliminar(Long id) { repo.deleteById(id); }
}
