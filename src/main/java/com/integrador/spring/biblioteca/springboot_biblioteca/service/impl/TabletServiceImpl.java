package com.integrador.spring.biblioteca.springboot_biblioteca.service.impl;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Tablet;
import com.integrador.spring.biblioteca.springboot_biblioteca.repository.TabletRepository;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.TabletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TabletServiceImpl implements TabletService {

    private final TabletRepository tabletRepository;

    @Override
    public List<Tablet> listarTodos() {
        return tabletRepository.findAll();
    }

    @Override
    public Optional<Tablet> buscarPorId(Long id) {
        return tabletRepository.findById(id);
    }

    @Override
    public Optional<Tablet> buscarPorSn(String sn) {
        return tabletRepository.findBySn(sn);
    }

    @Override
    public Tablet guardar(Tablet tablet) {
        return tabletRepository.save(tablet);
    }

    @Override
    public void eliminar(Long id) {
        tabletRepository.deleteById(id);
    }
}
