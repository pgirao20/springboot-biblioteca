// src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/controller/PrestamoController.java
package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Estudiante;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.EstudianteService;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.LibroService;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.PrestamoService;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.TabletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/prestamos")
@RequiredArgsConstructor
public class PrestamoController {

    private final EstudianteService estudianteService;
    private final LibroService libroService;
    private final TabletService tabletService;
    private final PrestamoService prestamoService;

    // ============================================
    //  PRÉSTAMOS DE LIBROS
    // ============================================
    @GetMapping("/libros")
    public String prestamosLibros(Model model) {

        var estudiantes = estudianteService.listarTodos();
        model.addAttribute("estudiantes", estudiantes);

        var librosDisponibles = libroService.listarDisponibles();
        model.addAttribute("libros", librosDisponibles);

        var estados = List.of("ACTIVO", "PENDIENTE");
        model.addAttribute("estados", estados);

        var prestamos = prestamoService.listarPrestamosLibros();
        model.addAttribute("prestamos", prestamos);

        model.addAttribute("hoy", LocalDate.now());

        return "admin/prestamos/prestamo_libros";
    }

   @PostMapping("/libros/registrar")
public String registrarPrestamoLibros(@RequestParam("codigoEstudiante") String codigoEstudiante,
                                      @RequestParam("fechaPrestamo") String fechaPrestamoStr,
                                      @RequestParam("fechaDevolucion") String fechaDevolucionStr,
                                      @RequestParam("sns") String snsCsv,
                                      RedirectAttributes redirect) {

    try {
        // ============================
        // 1) Validar sanción del estudiante
        // ============================
        Estudiante est = estudianteService.buscarPorCodigo(codigoEstudiante)
                .orElse(null);

        if (est == null) {
            redirect.addFlashAttribute("error", "Estudiante no encontrado.");
            return "redirect:/admin/prestamos/libros";
        }

        LocalDate hoy = LocalDate.now();
        if (est.getSancionadoHasta() != null && !hoy.isAfter(est.getSancionadoHasta())) {
            redirect.addFlashAttribute(
                    "error",
                    "El estudiante está sancionado hasta " + est.getSancionadoHasta()
                            + " y no puede solicitar préstamos de libros."
            );
            return "redirect:/admin/prestamos/libros";
        }

        // ============================
        // 2) Lógica actual de registro
        // ============================
        LocalDate fechaPrestamo = LocalDate.parse(fechaPrestamoStr);
        LocalDate fechaDevolucion = LocalDate.parse(fechaDevolucionStr);

        List<String> sns = Arrays.stream(snsCsv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        if (sns.isEmpty()) {
            redirect.addFlashAttribute("error", "Debes agregar al menos un libro a la prelista.");
            return "redirect:/admin/prestamos/libros";
        }

        prestamoService.registrarPrestamoLibros(
                codigoEstudiante, fechaPrestamo, fechaDevolucion, sns);

        redirect.addFlashAttribute("ok", "Préstamo registrado correctamente.");
    } catch (Exception e) {
        redirect.addFlashAttribute("error", "Error al registrar préstamo: " + e.getMessage());
    }

    return "redirect:/admin/prestamos/libros";
}

    // ============================================
    //  DEVOLUCIÓN DE LIBROS (desde el modal)
    // ============================================
    @PostMapping("/libros/devolver")
    public String devolverLibro(@RequestParam("codigoPrestamo") String codigoPrestamo,
                                @RequestParam("valorLibro") BigDecimal valorLibro,
                                @RequestParam("estadoLibro") String estadoLibro,
                                RedirectAttributes attr) {

        BigDecimal costo = BigDecimal.ZERO;

        if ("MANTENIMIENTO".equals(estadoLibro)) {
            costo = valorLibro.multiply(new BigDecimal("0.50"));  // 50% reparación
        } else if ("DAÑADO".equals(estadoLibro)) {
            costo = valorLibro;  // valor total del libro
        }

        prestamoService.registrarDevolucionLibro(codigoPrestamo, estadoLibro, costo);

        attr.addFlashAttribute("ok", "Devolución registrada correctamente.");
        return "redirect:/admin/prestamos/libros";
    }

    // ============================================
    //  DEVOLUCIÓN DE TABLETS (desde el modal)
    // ============================================
    @PostMapping("/tablets/devolver")
    public String devolverTablet(@RequestParam("codigoPrestamo") String codigoPrestamo,
                                @RequestParam("valorTablet") BigDecimal valorTablet,
                                @RequestParam("estadoTablet") String estadoTablet,
                                RedirectAttributes attr) {

        BigDecimal costo = BigDecimal.ZERO;

        if ("MANTENIMIENTO".equals(estadoTablet)) {
            costo = valorTablet.multiply(new BigDecimal("0.50")); // 50% reparación
        } else if ("DAÑADO".equals(estadoTablet)) {
            costo = valorTablet; // valor total de la tablet
        }

        prestamoService.registrarDevolucionTablet(codigoPrestamo, estadoTablet, costo);

        attr.addFlashAttribute("ok", "Devolución de tablet registrada correctamente.");
        return "redirect:/admin/prestamos/tablets";
    }


        // ============================================
        //  PRÉSTAMOS DE TABLETS 
        // ============================================
        @GetMapping("/tablets")
        public String prestamosTablets(Model model) {

            var estudiantes = estudianteService.listarTodos();
            var tabletsDisponibles = tabletService.listarDisponibles();
            var estados = List.of("ACTIVO", "PENDIENTE");

            var prestamos = prestamoService.listarPrestamosTablets();

            model.addAttribute("estudiantes", estudiantes);
            model.addAttribute("tablets", tabletsDisponibles);
            model.addAttribute("estados", estados);
            model.addAttribute("prestamos", prestamos);
            model.addAttribute("hoy", LocalDate.now());

            return "admin/prestamos/prestamos_tablets";
        }

   @PostMapping("/tablets/registrar")
public String registrarPrestamoTablets(@RequestParam("codigoEstudiante") String codigoEstudiante,
                                       @RequestParam("fechaPrestamo") String fechaPrestamoStr,
                                       @RequestParam("fechaDevolucion") String fechaDevolucionStr,
                                       @RequestParam("sns") String snsCsv,
                                       RedirectAttributes redirect) {

    try {
        // ============================
        // 1) Validar sanción del estudiante
        // ============================
        Estudiante est = estudianteService.buscarPorCodigo(codigoEstudiante)
                .orElse(null);

        if (est == null) {
            redirect.addFlashAttribute("error", "Estudiante no encontrado.");
            return "redirect:/admin/prestamos/tablets";
        }

        LocalDate hoy = LocalDate.now();
        if (est.getSancionadoHasta() != null && !hoy.isAfter(est.getSancionadoHasta())) {
            redirect.addFlashAttribute(
                    "error",
                    "El estudiante está sancionado hasta " + est.getSancionadoHasta()
                            + " y no puede solicitar préstamos de tablets."
            );
            return "redirect:/admin/prestamos/tablets";
        }

        // ============================
        // 2) Lógica actual de registro
        // ============================
        LocalDate fechaPrestamo = LocalDate.parse(fechaPrestamoStr);
        LocalDate fechaDevolucion = LocalDate.parse(fechaDevolucionStr);

        List<String> sns = Arrays.stream(snsCsv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        if (sns.isEmpty()) {
            redirect.addFlashAttribute("error", "Debes agregar al menos una tablet a la prelista.");
            //  Corrijo la URL: el GET es /admin/prestamos/tablets
            return "redirect:/admin/prestamos/tablets";
        }

        prestamoService.registrarPrestamoTablets(
                codigoEstudiante, fechaPrestamo, fechaDevolucion, sns);

        redirect.addFlashAttribute("ok", "Préstamo de tablets registrado correctamente.");
    } catch (Exception e) {
        redirect.addFlashAttribute("error", "Error al registrar préstamo: " + e.getMessage());
    }

    return "redirect:/admin/prestamos/tablets";
}

    // ============================================
    //  ELIMINAR PRÉSTAMO 
    // ============================================
    @PostMapping("/eliminar")
    public String eliminarPrestamo(@RequestParam("codigo") String codigo,
                                   @RequestParam("tipo") String tipo,
                                   RedirectAttributes redirect) {
        try {
            prestamoService.eliminarPrestamoPorCodigo(codigo);
            redirect.addFlashAttribute("ok", "Préstamo eliminado correctamente.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Error al eliminar préstamo: " + e.getMessage());
        }

        if ("tablets".equalsIgnoreCase(tipo)) {
            return "redirect:/admin/prestamos/tablets";
        } else {
            return "redirect:/admin/prestamos/libros";
        }
    }
    
}
