package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Editorial;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.IEditorialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/libros/editoriales")
public class EditorialController {
    private final IEditorialService service;
    public EditorialController(IEditorialService service) { this.service = service; }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("title", "Editoriales");
        model.addAttribute("active", "libros");
        model.addAttribute("editoriales", service.listar());
        model.addAttribute("nuevaEditorial", new Editorial());
        return "admin/libros-editoriales";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevaEditorial") Editorial editorial) {
        service.guardar(editorial);
        return "redirect:/admin/libros/editoriales";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("title", "Editoriales");
        model.addAttribute("active", "libros");
        model.addAttribute("editoriales", service.listar());
        model.addAttribute("nuevaEditorial", service.buscar(id).orElse(null));
        return "admin/libros-editoriales";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/admin/libros/editoriales";
    }
}
