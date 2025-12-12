// src/main/java/.../service/impl/SancionServiceImpl.java
package com.integrador.spring.biblioteca.springboot_biblioteca.service.impl;

import com.integrador.spring.biblioteca.springboot_biblioteca.dto.SancionDTO;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Estudiante;
import com.integrador.spring.biblioteca.springboot_biblioteca.repository.EstudianteRepository;
import com.integrador.spring.biblioteca.springboot_biblioteca.repository.PrestamoRepository;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.SancionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SancionServiceImpl implements SancionService {

    private static final int DIAS_SANCION = 7;

    private final PrestamoRepository prestamoRepository;
    private final EstudianteRepository estudianteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SancionDTO> listarSanciones() {
        LocalDate hoy = LocalDate.now();

        // Estudiantes que tienen al menos una multa (costoReparacion > 0)
        List<Estudiante> estudiantes = prestamoRepository.listarEstudiantesConSancionOMulta();

        return estudiantes.stream().map(est -> {
            BigDecimal monto = prestamoRepository.calcularMontoTotalMulta(est.getId());
            LocalDate fin = est.getSancionadoHasta(); // puede ser null
            LocalDate ultimaDevConMulta =
                    prestamoRepository.obtenerUltimaFechaDevolucionConMulta(est.getId());

            // inicioSancion: si hay fin, asumimos sanción de DIAS_SANCION
            LocalDate inicio;
            if (fin != null) {
                inicio = fin.minusDays(DIAS_SANCION);
            } else {
                // fallback: usar la última fecha de devolución con multa
                inicio = ultimaDevConMulta;
            }

            boolean disponible;
            if (fin == null || fin.isBefore(hoy)) {
                disponible = true;  // préstamo disponible
            } else {
                disponible = false; // tiene sanción vigente
            }

            return new SancionDTO(
                    est.getId(),
                    est.getDni(),
                    est.getCodigo(),
                    est.getNombres(),
                    est.getApellidos(),
                    inicio,
                    fin,
                    monto,
                    disponible
            );
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void quitarSancion(Long idEstudiante) {
        Estudiante est = estudianteRepository.findById(idEstudiante)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));

        // solo quitamos la sanción (no tocamos el monto)
        est.setSancionadoHasta(null);
        estudianteRepository.save(est);
    }

    @Override
    @Transactional
    public void borrarSancion(Long idEstudiante) {
        // Aquí decides qué significa "borrar":
        // opción A: solo quitar sanción (igual que quitarSancion)
        quitarSancion(idEstudiante);

        // opción B (si en el futuro quieres):
        // - poner en 0 las multas de sus préstamos
        // - o marcar algo en otra tabla
        // por ahora lo dejamos como alias de quitarSancion.
    }
}
