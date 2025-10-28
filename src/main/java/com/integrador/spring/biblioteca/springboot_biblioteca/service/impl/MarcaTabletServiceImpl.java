package com.integrador.spring.biblioteca.springboot_biblioteca.service.impl;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.MarcaTablet;
import com.integrador.spring.biblioteca.springboot_biblioteca.repository.MarcaTabletRepository;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.IMarcaTabletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarcaTabletServiceImpl implements IMarcaTabletService {

    private final MarcaTabletRepository marcaRepo;

    @Override
    public List<MarcaTablet> listarTodos() {
        return marcaRepo.findAll();
    }

    @Override
    public MarcaTablet guardar(MarcaTablet marca) {
        return marcaRepo.save(marca);
    }

    @Override
    public Optional<MarcaTablet> buscarPorId(Long id) {
        return marcaRepo.findById(id);
    }

    @Override
    public void eliminar(Long id) {
        marcaRepo.deleteById(id);
    }

    @Override
    public boolean existePorNombre(String nombre) {
        return marcaRepo.existsByNombreIgnoreCase(nombre);
    }
}
