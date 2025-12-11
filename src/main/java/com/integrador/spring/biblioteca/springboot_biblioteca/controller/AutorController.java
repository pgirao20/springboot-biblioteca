// src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/controller/AutorController.java
package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Autor;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.IAutorService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/libros/autores")
public class AutorController {

    private final IAutorService service;

    public AutorController(IAutorService service) {
        this.service = service;
    }

    // LISTAR AUTORES
    @GetMapping
    public String listar(Model model,
                         @ModelAttribute(value = "ok") String ok,
                         @ModelAttribute(value = "warn") String warn,
                         @ModelAttribute(value = "error") String error) {

        model.addAttribute("title", "Autores");
        model.addAttribute("active", "libros");
        model.addAttribute("autores", service.listar());
        model.addAttribute("nuevoAutor", new Autor());

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

        return "admin/libros-autores";
    }

    // GUARDAR AUTOR (crear o editar)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevoAutor") Autor autor,
                          RedirectAttributes redirectAttributes) {
        try {
            service.guardar(autor);
            redirectAttributes.addFlashAttribute("ok", "Autor guardado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ocurrió un error al guardar el autor.");
        }
        return "redirect:/admin/libros/autores";
    }

    // CARGAR AUTOR PARA EDICIÓN
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("title", "Autores");
        model.addAttribute("active", "libros");
        model.addAttribute("autores", service.listar());
        model.addAttribute("nuevoAutor", service.buscar(id).orElse(null));
        return "admin/libros-autores";
    }

    // ELIMINAR AUTOR (con manejo de FK)
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id,
                           RedirectAttributes redirectAttributes) {
        try {
            service.eliminar(id);
            redirectAttributes.addFlashAttribute("ok", "Autor eliminado correctamente.");
        } catch (DataIntegrityViolationException ex) {
            // Hay libros que referencian este autor
            redirectAttributes.addFlashAttribute(
                    "error",
                    "No se puede eliminar el autor porque hay libros asociados a él."
            );
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Ocurrió un error al intentar eliminar el autor."
            );
        }

        return "redirect:/admin/libros/autores";
    }
}
