<<<<<<< HEAD
=======
// src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/service/impl/PrestamoServiceImpl.java
>>>>>>> 8a693b5 (prestamos v2)
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
                    "P" + entity.getId(),                      // código visual, no en BD
                    nombreCompleto,
                    codigoEstudiante,
                    tituloLibro,
                    entity.getFechaPrestamo().toString(),
                    entity.getFechaDevolucion().toString(),
                    entity.getEstado().name()
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

<<<<<<< HEAD
    // ✅ Enviar correo una sola vez al final
=======
   
>>>>>>> 8a693b5 (prestamos v2)
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

        // préstamos donde tablet != null (es decir, préstamo de tablet)
        for (PrestamoEntity p : prestamoRepository.findByTabletIsNotNullOrderByFechaPrestamoDesc()) {

            Estudiante e = p.getEstudiante();
            Tablet t = p.getTablet();

            String nombreCompleto = e.getNombres() + " " + e.getApellidos();
            String codigoEst = e.getCodigo();
            String nombreTablet = (t != null) ? t.getSn() + " - " + t.getModelo(): "(sin tablet)";

            Prestamo dto = new Prestamo(
                    "T" + p.getId(),
                    nombreCompleto,
                    codigoEst,
                    nombreTablet,
                    p.getFechaPrestamo().toString(),
                    p.getFechaDevolucion().toString(),
                    p.getEstado().name()
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

    // mismas validaciones que en libros...
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

        // Para el correo: "SNXXX - Modelo Tablet"
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

@Override
@Transactional
public void eliminarPrestamoPorCodigo(String codigo) {
    if (codigo == null || codigo.length() < 2) {
        throw new IllegalArgumentException("Código de préstamo inválido");
    }

    // Asumimos que el código es algo como "P5" o "T7"
    Long id;
    try {
        id = Long.parseLong(codigo.substring(1)); // salta la primera letra
    } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Formato de código de préstamo inválido: " + codigo);
    }

    eliminarPrestamoPorId(id);
}

@Transactional
public void eliminarPrestamoPorId(Long id) {

    PrestamoEntity p = prestamoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

    // Si era libro: devolver disponibilidad
    if (p.getLibro() != null) {
        Libro libro = p.getLibro();
        libro.setEstado("DISPONIBLE");
        libroService.guardar(libro);
    }

    // Si era tablet: devolver disponibilidad
    if (p.getTablet() != null) {
        Tablet tablet = p.getTablet();
        tablet.setEstado("DISPONIBLE");
        tabletService.guardar(tablet);
    }

    // Eliminar préstamo
    prestamoRepository.deleteById(id);
}

}

