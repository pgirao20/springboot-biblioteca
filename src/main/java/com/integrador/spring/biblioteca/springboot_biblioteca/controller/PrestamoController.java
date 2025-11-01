package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Prestamo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class PrestamoController {

    @GetMapping("/admin/prestamos/libros")
    public String prestamosLibros(Model model) {

        List<String> estudiantes = Arrays.asList(
                "2024001 - María López",
                "2024002 - Carlos Pérez",
                "2024003 - Lucía Ramírez",
                "2024004 - José García"
        );

        List<String> libros = Arrays.asList(
                "L001 - Cien años de soledad",
                "L002 - El origen de las especies",
                "L003 - Introducción a Java",
                "L004 - Don Quijote de la Mancha"
        );

        List<String> estados = Arrays.asList("Activo", "Pendiente", "Vencido", "Devuelto");

        List<Prestamo> prestamos = Arrays.asList(
                new Prestamo("P20250928001", "María López", "2024001",
                        "Cien años de soledad", "2025-09-28", "2025-10-05", "Activo"),
                new Prestamo("P20250920002", "Carlos Pérez", "2024002",
                        "El origen de las especies", "2025-09-20", "2025-09-27", "Vencido"),
                new Prestamo("P20250922003", "Lucía Ramírez", "2024003",
                        "Introducción a Java", "2025-09-22", "2025-09-29", "Pendiente"),
                new Prestamo("P20250910004", "José García", "2024004",
                        "Don Quijote de la Mancha", "2025-09-10", "2025-09-20", "Devuelto")
        );

        model.addAttribute("estudiantes", estudiantes);
        model.addAttribute("libros", libros);
        model.addAttribute("estados", estados);
        model.addAttribute("prestamos", prestamos);

        return "admin/prestamos/prestamo_libros";
    }

    @GetMapping("/admin/prestamos/tablets")
    public String prestamosTablets(Model model) {

        List<String> estudiantes = Arrays.asList(
                "2024001 - María López",
                "2024002 - Carlos Pérez",
                "2024003 - Lucía Ramírez"
        );

        List<String> tablets = Arrays.asList(
                "T001 - Samsung Galaxy Tab A9",
                "T002 - Lenovo Smart Tab M10",
                "T003 - Huawei MatePad SE"
        );

        List<String> estados = Arrays.asList("Activo", "Pendiente", "Vencido", "Devuelto");

        List<Prestamo> prestamos = Arrays.asList(
                new Prestamo("TP2025100101", "María López", "2024001",
                        "Samsung Galaxy Tab A9", "2025-10-01", "2025-10-08", "Activo"),
                new Prestamo("TP2025092202", "Carlos Pérez", "2024002",
                        "Huawei MatePad SE", "2025-09-22", "2025-09-29", "Pendiente")
        );

        model.addAttribute("estudiantes", estudiantes);
        model.addAttribute("tablets", tablets);
        model.addAttribute("estados", estados);
        model.addAttribute("prestamos", prestamos);

        return "admin/prestamos/prestamos_tablets";
    }
}
