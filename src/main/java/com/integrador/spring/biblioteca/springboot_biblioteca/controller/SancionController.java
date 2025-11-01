package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Sancion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class SancionController {

    @GetMapping("/admin/sanciones")
    public String listarSanciones(Model model) {

        
        List<Sancion> sanciones = Arrays.asList(
                new Sancion("S001", "2024001", "María López",
                        "Retraso en devolución de libro", "2025-09-10", "2025-09-20", 5.00, "Pendiente"),
                new Sancion("S002", "2024002", "Carlos Pérez",
                        "Pérdida de libro", "2025-09-15", "2025-09-25", 30.00, "Pagado"),
                new Sancion("S003", "2024003", "Lucía Ramírez",
                        "Daño parcial del libro", "2025-09-18", "2025-09-28", 10.00, "Pendiente"),
                new Sancion("S004", "2024004", "José García",
                        "Retraso en devolución de tablet", "2025-09-22", "2025-09-30", 8.00, "Pendiente")
        );

        model.addAttribute("sanciones", sanciones);

       
        model.addAttribute("estados", Arrays.asList("Pendiente", "Pagado", "Anulado"));

        return "admin/sanciones/sanciones";
    }
}
