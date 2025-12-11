// src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/service/impl/editorialServiceImpl.java
package com.integrador.spring.biblioteca.springboot_biblioteca.service.impl;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Editorial;
import com.integrador.spring.biblioteca.springboot_biblioteca.repository.EditorialRepository;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.IEditorialService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EditorialServiceImpl implements IEditorialService {
    private final EditorialRepository repo;
    public EditorialServiceImpl(EditorialRepository repo) { this.repo = repo; }
    public List<Editorial> listar() { return repo.findAll(); }
    public Editorial guardar(Editorial e) { return repo.save(e); }
    public Optional<Editorial> buscar(Long id) { return repo.findById(id); }
    public void eliminar(Long id) { repo.deleteById(id); }
}
