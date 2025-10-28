package com.integrador.spring.biblioteca.springboot_biblioteca.service.impl;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.MarcaTablet;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Tablet;
import com.integrador.spring.biblioteca.springboot_biblioteca.repository.MarcaTabletRepository;
import com.integrador.spring.biblioteca.springboot_biblioteca.repository.TabletRepository;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.ITabletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TabletServiceImpl implements ITabletService {

    private final TabletRepository tabletRepo;
    private final MarcaTabletRepository marcaRepo;

    @Override
    public List<Tablet> listarTodos() {
        return tabletRepo.findAll();
    }

    @Transactional
    @Override
    public Tablet guardar(Tablet tablet) {
        // Validación de SN único con mensaje claro
        if (tablet.getSn() == null || tablet.getSn().isBlank()) {
            throw new RuntimeException("El número de serie (SN) es obligatorio.");
        }
        if (tablet.getId() == null && tabletRepo.existsBySn(tablet.getSn())) {
            throw new RuntimeException("El número de serie " + tablet.getSn() + " ya está registrado.");
        }
        // Si viene sin estado, por seguridad setear por defecto
        if (tablet.getEstado() == null || tablet.getEstado().isBlank()) {
            tablet.setEstado("Disponible");
        }
        return tabletRepo.save(tablet);
    }

    @Override
    public Optional<Tablet> buscarPorId(Long id) {
        return tabletRepo.findById(id);
    }

    @Transactional
    @Override
    public void eliminar(Long id) {
        tabletRepo.deleteById(id);
    }

    @Override
    public boolean existeSn(String sn) {
        return tabletRepo.existsBySn(sn);
    }

    @Transactional
    @Override
    public Map<String, Object> guardarLote(Long marcaId, String modelo, Integer anioAdquisicion, List<String> sns,
                                           String estadoSiEdita) {
        Map<String, Object> res = new HashMap<>();
        List<String> duplicados = new ArrayList<>();
        int creados = 0;

        MarcaTablet marca = marcaRepo.findById(marcaId)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        for (String sn : sns) {
            String limpio = sn == null ? "" : sn.trim();
            if (limpio.isEmpty()) continue;

            if (tabletRepo.existsBySn(limpio)) {
                duplicados.add(limpio);
                continue;
            }

            Tablet t = Tablet.builder()
                    .marca(marca)
                    .modelo(modelo)
                    .sn(limpio)
                    .anioAdquisicion(anioAdquisicion)
                    .estado((estadoSiEdita == null || estadoSiEdita.isBlank()) ? "Disponible" : estadoSiEdita)
                    .build();

            tabletRepo.save(t);
            creados++;
        }

        res.put("creados", creados);
        res.put("duplicados", duplicados);
        return res;
    }
}
