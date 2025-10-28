// service/impl/AutorServiceImpl.java
package com.integrador.spring.biblioteca.springboot_biblioteca.service.impl;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Autor;
import com.integrador.spring.biblioteca.springboot_biblioteca.repository.AutorRepository;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.IAutorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorServiceImpl implements IAutorService {
    private final AutorRepository repo;
    public AutorServiceImpl(AutorRepository repo) { this.repo = repo; }
    public List<Autor> listar() { return repo.findAll(); }
    public Autor guardar(Autor a) { return repo.save(a); }
    public Optional<Autor> buscar(Long id) { return repo.findById(id); }
    public void eliminar(Long id) { repo.deleteById(id); }
}
