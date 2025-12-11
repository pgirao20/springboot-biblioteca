// src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/service/impl/AreaServiceImpl.java
package com.integrador.spring.biblioteca.springboot_biblioteca.service.impl;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Area;
import com.integrador.spring.biblioteca.springboot_biblioteca.repository.AreaRepository;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.IAreaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AreaServiceImpl implements IAreaService {
    private final AreaRepository repo;
    public AreaServiceImpl(AreaRepository repo) { this.repo = repo; }
    public List<Area> listar() { return repo.findAll(); }
    public Area guardar(Area a) { return repo.save(a); }
    public Optional<Area> buscar(Long id) { return repo.findById(id); }
    public void eliminar(Long id) { repo.deleteById(id); }
}
