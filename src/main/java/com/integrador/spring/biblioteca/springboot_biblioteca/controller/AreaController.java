// src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/controller/AreaController.java
package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Area;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.IAreaService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/libros/areas")
public class AreaController {

    private final IAreaService service;

    public AreaController(IAreaService service) {
        this.service = service;
    }

    // LISTAR ÁREAS
    @GetMapping
    public String listar(Model model,
                         @ModelAttribute(value = "ok") String ok,
                         @ModelAttribute(value = "warn") String warn,
                         @ModelAttribute(value = "error") String error) {

        model.addAttribute("title", "Áreas");
        model.addAttribute("active", "libros");
        model.addAttribute("areas", service.listar());
        model.addAttribute("nuevaArea", new Area());

        // Pasar mensajes flash si existen
        if (ok != null && !ok.isEmpty()) {
            model.addAttribute("ok", ok);
        }
        if (warn != null && !warn.isEmpty()) {
            model.addAttribute("warn", warn);
        }
        if (error != null && !error.isEmpty()) {
            model.addAttribute("error", error);
        }

        return "admin/libros-areas";
    }

    // GUARDAR ÁREA (crear o editar)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevaArea") Area area,
                          RedirectAttributes redirectAttributes) {
        try {
            service.guardar(area);
            redirectAttributes.addFlashAttribute("ok", "Área guardada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ocurrió un error al guardar el área.");
        }
        return "redirect:/admin/libros/areas";
    }

    // CARGAR ÁREA PARA EDICIÓN
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        model.addAttribute("title", "Áreas");
        model.addAttribute("active", "libros");
        model.addAttribute("areas", service.listar());
        model.addAttribute("nuevaArea", service.buscar(id).orElse(null));

        return "admin/libros-areas";
    }

    // ELIMINAR ÁREA
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id,
                           RedirectAttributes redirectAttributes) {
        try {
            service.eliminar(id);
            redirectAttributes.addFlashAttribute("ok", "Área eliminada correctamente.");
        } catch (DataIntegrityViolationException ex) {
            // Hay libros que referencian esta área
            redirectAttributes.addFlashAttribute(
                    "error",
                    "No se puede eliminar el área porque hay libros asociados a ella."
            );
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Ocurrió un error al intentar eliminar el área."
            );
        }

        return "redirect:/admin/libros/areas";
    }
}
