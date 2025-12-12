// src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/service/impl/PrestamoServiceImpl.java 

package com.integrador.spring.biblioteca.springboot_biblioteca.service.impl;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.*;
import com.integrador.spring.biblioteca.springboot_biblioteca.repository.PrestamoRepository;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.EmailService;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.EstudianteService;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.LibroService;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.PrestamoService;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.TabletService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final EstudianteService estudianteService;
    private final LibroService libroService;
    private final TabletService tabletService;
    private final EmailService emailService;

    // ================== LIBROS: LISTAR ==================
    @Override
    public List<Prestamo> listarPrestamosLibros() {
        List<Prestamo> resultado = new ArrayList<>();

        for (PrestamoEntity entity : prestamoRepository.findByTabletIsNullOrderByFechaPrestamoDesc()) {
            Estudiante e = entity.getEstudiante();
            Libro l = entity.getLibro();

            String nombreCompleto = e.getNombres() + " " + e.getApellidos();
            String codigoEstudiante = e.getCodigo();
            String tituloLibro = (l != null) ? l.getSn() + " - " + l.getTitulo() : "(Sin libro)";

            Prestamo p = new Prestamo(
                    "P" + entity.getId(),
                    nombreCompleto,
                    codigoEstudiante,
                    tituloLibro,
                    entity.getFechaPrestamo().toString(),
                    entity.getFechaDevolucion().toString(),
                    entity.getEstado().name(),
                    tituloLibro,
                    null
            );
            resultado.add(p);
        }

        return resultado;
    }

    // ================== LIBROS: REGISTRAR ==================
    @Override
    @Transactional
    public void registrarPrestamoLibros(String codigoEstudiante,
                                        LocalDate fechaPrestamo,
                                        LocalDate fechaDevolucion,
                                        List<String> sns) {

        if (fechaPrestamo == null || fechaPrestamo.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de préstamo debe ser hoy o mayor.");
        }

        if (fechaDevolucion == null || !fechaDevolucion.isAfter(fechaPrestamo)) {
            throw new IllegalArgumentException("La fecha de devolución debe ser al menos un día después del préstamo.");
        }

        Estudiante estudiante = estudianteService.buscarPorCodigo(codigoEstudiante)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe un estudiante con código: " + codigoEstudiante));

        List<String> itemsCorreo = new java.util.ArrayList<>();

        for (String sn : sns) {
            if (sn == null || sn.isBlank()) continue;

            Libro libro = libroService.buscarPorSn(sn.trim())
                    .orElseThrow(() -> new IllegalArgumentException("No existe libro con SN: " + sn));

            if (!"DISPONIBLE".equalsIgnoreCase(libro.getEstado())) {
                throw new IllegalStateException("El libro con SN " + sn + " no está disponible.");
            }

            PrestamoEntity entity = new PrestamoEntity();
            entity.setEstado(EstadoPrestamo.ACTIVO);
            entity.setFechaPrestamo(fechaPrestamo);
            entity.setFechaDevolucion(fechaDevolucion);
            entity.setEstudiante(estudiante);
            entity.setLibro(libro);
            entity.setTablet(null);

            prestamoRepository.save(entity);

            libro.setEstado("PRESTADO");
            libroService.guardar(libro);

            // Para el correo: "SNXXX - Título del libro"
            itemsCorreo.add(libro.getSn() + " - " + libro.getTitulo());
        }

        if (!itemsCorreo.isEmpty()) {
            emailService.enviarCorreoPrestamo(
                    estudiante,
                    "LIBRO",
                    fechaPrestamo,
                    fechaDevolucion,
                    itemsCorreo
            );
        }
    }

    // ================== TABLETS: LISTAR ==================
    @Override
    public List<Prestamo> listarPrestamosTablets() {
        List<Prestamo> resultado = new ArrayList<>();

        for (PrestamoEntity p : prestamoRepository.findByTabletIsNotNullOrderByFechaPrestamoDesc()) {

            Estudiante e = p.getEstudiante();
            Tablet t = p.getTablet();

            String nombreCompleto = e.getNombres() + " " + e.getApellidos();
            String codigoEst = e.getCodigo();
            String nombreTablet = (t != null) ? t.getSn() + " - " + t.getModelo() : "(sin tablet)";

            Prestamo dto = new Prestamo(
                    "T" + p.getId(),
                    nombreCompleto,
                    codigoEst,
                    nombreTablet,
                    p.getFechaPrestamo().toString(),
                    p.getFechaDevolucion().toString(),
                    p.getEstado().name(),
                    nombreTablet,
                    null
            );
            resultado.add(dto);
        }

        return resultado;
    }

    // ================== TABLETS: REGISTRAR ==================
    @Override
    @Transactional
    public void registrarPrestamoTablets(String codigoEstudiante,
                                         LocalDate fechaPrestamo,
                                         LocalDate fechaDevolucion,
                                         List<String> snsTablets) {

        Estudiante estudiante = estudianteService.buscarPorCodigo(codigoEstudiante)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe un estudiante con código: " + codigoEstudiante));

        List<String> itemsCorreo = new java.util.ArrayList<>();

        for (String sn : snsTablets) {
            if (sn == null || sn.isBlank()) continue;

            Tablet tablet = tabletService.buscarPorSn(sn.trim())
                    .orElseThrow(() -> new IllegalArgumentException("No existe tablet con SN: " + sn));

            if (!"DISPONIBLE".equalsIgnoreCase(tablet.getEstado())) {
                throw new IllegalStateException("La tablet con SN " + sn + " no está disponible.");
            }

            PrestamoEntity entity = new PrestamoEntity();
            entity.setEstado(EstadoPrestamo.ACTIVO);
            entity.setFechaPrestamo(fechaPrestamo);
            entity.setFechaDevolucion(fechaDevolucion);
            entity.setEstudiante(estudiante);
            entity.setLibro(null);
            entity.setTablet(tablet);

            prestamoRepository.save(entity);

            tablet.setEstado("PRESTADO");
            tabletService.guardar(tablet);

            itemsCorreo.add(tablet.getSn() + " - " + tablet.getModelo());
        }

        if (!itemsCorreo.isEmpty()) {
            emailService.enviarCorreoPrestamo(
                    estudiante,
                    "TABLET",
                    fechaPrestamo,
                    fechaDevolucion,
                    itemsCorreo
            );
        }
    }

 // ================== LIBROS: REGISTRAR DEVOLUCION ==================
    @Override
    @Transactional
    public void registrarDevolucionLibro(String codigoPrestamo,
                                        String estadoLibro,
                                        BigDecimal costoReparacion) {

        if (codigoPrestamo == null || codigoPrestamo.length() < 2) {
            throw new IllegalArgumentException("Código de préstamo inválido");
        }

        Long id;
        try {
            id = Long.parseLong(codigoPrestamo.substring(1)); // "P5" -> 5
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato de código de préstamo inválido: " + codigoPrestamo);
        }

        PrestamoEntity p = prestamoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

        if (p.getLibro() == null) {
            throw new IllegalStateException("El préstamo indicado no corresponde a un libro.");
        }

        // marcar préstamo como devuelto
        p.setEstado(EstadoPrestamo.DEVUELTO);
        p.setFechaDevolucionReal(LocalDate.now()); // si usas el campo extra

            //  aplicar sanción si se pasó de la fecha de devolución
        aplicarSancionSiCorresponde(p);


        // actualizar estado del libro
        Libro libro = p.getLibro();
        if (estadoLibro != null && !estadoLibro.isBlank()) {
            libro.setEstado(estadoLibro); // DISPONIBLE / MANTENIMIENTO / DAÑADO
        }

        // guardar costo de reparación
        if (costoReparacion != null) {
            p.setCostoReparacion(costoReparacion);
        } else {
            p.setCostoReparacion(BigDecimal.ZERO);
        }

        libroService.guardar(libro);
        prestamoRepository.save(p);
    }


    @Override
    @Transactional
    public void eliminarPrestamoPorCodigo(String codigo) {
        if (codigo == null || codigo.length() < 2) {
            throw new IllegalArgumentException("Código de préstamo inválido");
        }

        Long id;
        try {
            id = Long.parseLong(codigo.substring(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato de código de préstamo inválido: " + codigo);
        }

        eliminarPrestamoPorId(id);
    }

    @Transactional
    public void eliminarPrestamoPorId(Long id) {

        PrestamoEntity p = prestamoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

        if (p.getLibro() != null) {
            Libro libro = p.getLibro();
            libro.setEstado("DISPONIBLE");
            libroService.guardar(libro);
        }

        if (p.getTablet() != null) {
            Tablet tablet = p.getTablet();
            tablet.setEstado("DISPONIBLE");
            tabletService.guardar(tablet);
        }

        prestamoRepository.deleteById(id);
    }

     // ================== TABLETS: REGISTRAR DEVOLUCION ==================

    @Override
    @Transactional
    public void registrarDevolucionTablet(String codigoPrestamo,
                                        String estadoTablet,
                                        BigDecimal costoReparacion) {

        if (codigoPrestamo == null || codigoPrestamo.length() < 2) {
            throw new IllegalArgumentException("Código de préstamo inválido");
        }

        Long id;
        try {
            // "T5" -> 5
            id = Long.parseLong(codigoPrestamo.substring(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato de código de préstamo inválido: " + codigoPrestamo);
        }

        PrestamoEntity p = prestamoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

        if (p.getTablet() == null) {
            throw new IllegalStateException("El préstamo indicado no corresponde a una tablet.");
        }

        LocalDate hoy = LocalDate.now();

        // marcar préstamo como devuelto
        p.setEstado(EstadoPrestamo.DEVUELTO);
        p.setFechaDevolucionReal(hoy);

        //  MISMA LÓGICA DE SANCIÓN
        aplicarSancionSiCorresponde(p);

        // actualizar estado de la tablet
        Tablet tablet = p.getTablet();
        if (estadoTablet != null && !estadoTablet.isBlank()) {
            tablet.setEstado(estadoTablet); // DISPONIBLE / MANTENIMIENTO / DAÑADO
        }

        // costo reparación
        if (costoReparacion != null) {
            p.setCostoReparacion(costoReparacion);
        } else {
            p.setCostoReparacion(BigDecimal.ZERO);
        }

        tabletService.guardar(tablet);
        prestamoRepository.save(p);
    }

    // ================== SANCIONES ==================

    private void aplicarSancionSiCorresponde(PrestamoEntity p) {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaLimite = p.getFechaDevolucion();

        // Si no hay fecha límite o todavía no se pasó, no se sanciona
        if (fechaLimite == null || !hoy.isAfter(fechaLimite)) {
            return;
        }

        Estudiante est = p.getEstudiante();
        if (est == null) {
            return;
        }

        // Número de días de sanción (ajusta a lo que quieras)
        int diasSancion = 7;
        LocalDate nuevaFechaSancion = hoy.plusDays(diasSancion);

        LocalDate actual = est.getSancionadoHasta();

        // Si no estaba sancionado o ya venció la sanción, aplicar nueva
        if (actual == null || actual.isBefore(hoy)) {
            est.setSancionadoHasta(nuevaFechaSancion);
        } else if (actual.isBefore(nuevaFechaSancion)) {
            // Opcional: extender sanción si la nueva es mayor
            est.setSancionadoHasta(nuevaFechaSancion);
        }

        // MUY IMPORTANTE: guardar el estudiante
        estudianteService.guardar(est); // o save(est), como se llame en tu service
    }
}
