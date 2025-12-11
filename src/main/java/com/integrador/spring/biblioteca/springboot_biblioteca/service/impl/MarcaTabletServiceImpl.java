// src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/service/impl/MarcaTabletServiceImpl.java
package com.integrador.spring.biblioteca.springboot_biblioteca.service.impl;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.MarcaTablet;
import com.integrador.spring.biblioteca.springboot_biblioteca.repository.MarcaTabletRepository;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.MarcaTabletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarcaTabletServiceImpl implements MarcaTabletService {

    private final MarcaTabletRepository marcaTabletRepository;

    @Override
    public List<MarcaTablet> listarTodas() {
        return marcaTabletRepository.findAll();
    }
}
