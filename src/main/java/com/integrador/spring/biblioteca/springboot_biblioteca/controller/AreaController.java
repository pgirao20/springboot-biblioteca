package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Area;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.IAreaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/libros/areas")
public class AreaController {
    private final IAreaService service;
    public AreaController(IAreaService service) { this.service = service; }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("title", "Áreas");
        model.addAttribute("active", "libros");
        model.addAttribute("areas", service.listar());
        model.addAttribute("nuevaArea", new Area());
        return "admin/libros-areas";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevaArea") Area area) {
        service.guardar(area);
        return "redirect:/admin/libros/areas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("title", "Áreas");
        model.addAttribute("active", "libros");
        model.addAttribute("areas", service.listar());
        model.addAttribute("nuevaArea", service.buscar(id).orElse(null));
        return "admin/libros-areas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/admin/libros/areas";
    }
}
