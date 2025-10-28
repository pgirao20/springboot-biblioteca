package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Autor;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.IAutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/libros/autores")
public class AutorController {
    private final IAutorService service;
    public AutorController(IAutorService service) { this.service = service; }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("title", "Autores");
        model.addAttribute("active", "libros");
        model.addAttribute("autores", service.listar());
        model.addAttribute("nuevoAutor", new Autor());
        return "admin/libros-autores";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevoAutor") Autor autor) {
        service.guardar(autor);
        return "redirect:/admin/libros/autores";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("title", "Autores");
        model.addAttribute("active", "libros");
        model.addAttribute("autores", service.listar());
        model.addAttribute("nuevoAutor", service.buscar(id).orElse(null));
        return "admin/libros-autores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/admin/libros/autores";
    }
}
