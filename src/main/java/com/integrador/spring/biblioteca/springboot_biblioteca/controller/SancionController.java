// src/main/java/.../controller/SancionController.java
package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import com.integrador.spring.biblioteca.springboot_biblioteca.service.SancionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/sanciones")
@RequiredArgsConstructor
public class SancionController {

    private final SancionService sancionService;

    @GetMapping
    public String listarSanciones(Model model,
                                  @RequestParam(required = false) String ok,
                                  @RequestParam(required = false) String error) {

        model.addAttribute("sanciones", sancionService.listarSanciones());
        model.addAttribute("ok", ok);
        model.addAttribute("error", error);

        return "admin/sanciones";
    }

    @PostMapping("/quitar")
    public String quitarSancion(@RequestParam("idEstudiante") Long idEstudiante,
                                RedirectAttributes ra) {
        try {
            sancionService.quitarSancion(idEstudiante);
            ra.addAttribute("ok", "Sanción quitada correctamente. El estudiante ahora puede realizar préstamos.");
        } catch (Exception e) {
            ra.addAttribute("error", "Error al quitar sanción: " + e.getMessage());
        }
        return "redirect:/admin/sanciones";
    }

    @PostMapping("/borrar")
    public String borrarSancion(@RequestParam("idEstudiante") Long idEstudiante,
                                RedirectAttributes ra) {
        try {
            sancionService.borrarSancion(idEstudiante);
            ra.addAttribute("ok", "Sanción borrada correctamente.");
        } catch (Exception e) {
            ra.addAttribute("error", "Error al borrar sanción: " + e.getMessage());
        }
        return "redirect:/admin/sanciones";
    }
}
