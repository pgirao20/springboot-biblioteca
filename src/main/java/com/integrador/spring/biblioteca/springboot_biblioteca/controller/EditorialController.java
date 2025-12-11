// src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/controller/EditorialController.java
package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Editorial;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.IEditorialService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/libros/editoriales")
public class EditorialController {

    private final IEditorialService service;

    public EditorialController(IEditorialService service) {
        this.service = service;
    }

    // LISTAR EDITORIALES
    @GetMapping
    public String listar(Model model,
                         @ModelAttribute(value = "ok") String ok,
                         @ModelAttribute(value = "warn") String warn,
                         @ModelAttribute(value = "error") String error) {

        model.addAttribute("title", "Editoriales");
        model.addAttribute("active", "libros");
        model.addAttribute("editoriales", service.listar());
        model.addAttribute("nuevaEditorial", new Editorial());

        // Mensajes flash
        if (ok != null && !ok.isEmpty()) {
            model.addAttribute("ok", ok);
        }
        if (warn != null && !warn.isEmpty()) {
            model.addAttribute("warn", warn);
        }
        if (error != null && !error.isEmpty()) {
            model.addAttribute("error", error);
        }

        return "admin/libros-editoriales";
    }

    // GUARDAR EDITORIAL (crear o editar)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevaEditorial") Editorial editorial,
                          RedirectAttributes redirectAttributes) {
        try {
            service.guardar(editorial);
            redirectAttributes.addFlashAttribute("ok", "Editorial guardada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ocurrió un error al guardar la editorial.");
        }
        return "redirect:/admin/libros/editoriales";
    }

    // CARGAR EDITORIAL PARA EDICIÓN
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("title", "Editoriales");
        model.addAttribute("active", "libros");
        model.addAttribute("editoriales", service.listar());
        model.addAttribute("nuevaEditorial", service.buscar(id).orElse(null));
        return "admin/libros-editoriales";
    }

    // ELIMINAR EDITORIAL (con manejo de FK)
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id,
                           RedirectAttributes redirectAttributes) {
        try {
            service.eliminar(id);
            redirectAttributes.addFlashAttribute("ok", "Editorial eliminada correctamente.");
        } catch (DataIntegrityViolationException ex) {
            // Hay libros que referencian esta editorial
            redirectAttributes.addFlashAttribute(
                    "error",
                    "No se puede eliminar la editorial porque hay libros asociados a ella."
            );
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Ocurrió un error al intentar eliminar la editorial."
            );
        }

        return "redirect:/admin/libros/editoriales";
    }
}
