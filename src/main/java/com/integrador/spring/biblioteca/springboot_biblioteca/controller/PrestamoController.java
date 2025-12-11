// src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/controller/PrestamoController.java
package com.integrador.spring.biblioteca.springboot_biblioteca.controller;
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

    @GetMapping("/libros")
    public String prestamosLibros(Model model) {

        // Estudiantes como los tengas (BD o estático)
        var estudiantes = estudianteService.listarTodos();
        model.addAttribute("estudiantes", estudiantes);

        var librosDisponibles = libroService.listarDisponibles();
        model.addAttribute("libros", librosDisponibles);

        // Estados y préstamos como ya los tengas
        var estados = java.util.List.of("ACTIVO", "PENDIENTE");
        model.addAttribute("estados", estados);

        var prestamos = prestamoService.listarPrestamosLibros();
        model.addAttribute("prestamos", prestamos);

        model.addAttribute("hoy", java.time.LocalDate.now());

        return "admin/prestamos/prestamo_libros"; 
    }

    @PostMapping("/libros/registrar")
    public String registrarPrestamoLibros(@RequestParam("codigoEstudiante") String codigoEstudiante,
                                          @RequestParam("fechaPrestamo") String fechaPrestamoStr,
                                          @RequestParam("fechaDevolucion") String fechaDevolucionStr,
                                          @RequestParam("sns") String snsCsv,
                                          RedirectAttributes redirect) {

        try {
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

            prestamoService.registrarPrestamoLibros(codigoEstudiante, fechaPrestamo, fechaDevolucion, sns);

            redirect.addFlashAttribute("ok", "Préstamo registrado correctamente.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Error al registrar préstamo: " + e.getMessage());
        }

        return "redirect:/admin/prestamos/libros";
    }

   @GetMapping("/tablets")
public String prestamosTablets(Model model) {

    var estudiantes = estudianteService.listarTodos();
    var tabletsDisponibles = tabletService.listarDisponibles();
    var estados = java.util.List.of("ACTIVO", "PENDIENTE");

    var prestamos = prestamoService.listarPrestamosTablets();

    model.addAttribute("estudiantes", estudiantes);
    model.addAttribute("tablets", tabletsDisponibles);
    model.addAttribute("estados", estados);
    model.addAttribute("prestamos", prestamos);
    model.addAttribute("hoy", java.time.LocalDate.now());

    return "admin/prestamos/prestamos_tablets";
}


@PostMapping("/tablets/registrar")
public String registrarPrestamoTablets(@RequestParam("codigoEstudiante") String codigoEstudiante,
                                       @RequestParam("fechaPrestamo") String fechaPrestamoStr,
                                       @RequestParam("fechaDevolucion") String fechaDevolucionStr,
                                       @RequestParam("sns") String snsCsv,
                                       RedirectAttributes redirect) {

    try {
        LocalDate fechaPrestamo = LocalDate.parse(fechaPrestamoStr);
        LocalDate fechaDevolucion = LocalDate.parse(fechaDevolucionStr);

        List<String> sns = Arrays.stream(snsCsv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        if (sns.isEmpty()) {
            redirect.addFlashAttribute("error", "Debes agregar al menos una tablet a la prelista.");
            return "redirect:/admin/prestamos/prestamos_tablets";
        }

        prestamoService.registrarPrestamoTablets(
                codigoEstudiante, fechaPrestamo, fechaDevolucion, sns);

        redirect.addFlashAttribute("ok", "Préstamo de tablets registrado correctamente.");
    } catch (Exception e) {
        redirect.addFlashAttribute("error", "Error al registrar préstamo: " + e.getMessage());
    }

    return "redirect:/admin/prestamos/tablets";
}

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

    // Redirigir según origen
    if ("tablets".equalsIgnoreCase(tipo)) {
        return "redirect:/admin/prestamos/tablets";
    } else {
        return "redirect:/admin/prestamos/libros";
    }
}



}
