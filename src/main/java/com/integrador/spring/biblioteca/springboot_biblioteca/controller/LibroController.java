package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Libro;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.IAreaService;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.IAutorService;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.IEditorialService;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.ILibroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/libros")
public class LibroController {

    private final ILibroService libroService;
    private final IAutorService autorService;
    private final IEditorialService editorialService;
    private final IAreaService areaService;

    public LibroController(ILibroService libroService, IAutorService autorService,
                           IEditorialService editorialService, IAreaService areaService) {
        this.libroService = libroService;
        this.autorService = autorService;
        this.editorialService = editorialService;
        this.areaService = areaService;
    }

    @GetMapping
    public String listado(Model model) {
        model.addAttribute("title", "Gestión de Libros");
        model.addAttribute("active", "libros");

        model.addAttribute("libros", libroService.listar());
        model.addAttribute("autores", autorService.listar());
        model.addAttribute("editoriales", editorialService.listar());
        model.addAttribute("areas", areaService.listar());

        model.addAttribute("nuevoLibro", new Libro());
        return "admin/libros";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevoLibro") Libro libro) {
        libroService.guardar(libro);
        return "redirect:/admin/libros";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        var libro = libroService.buscar(id).orElse(null);

        model.addAttribute("title", "Editar Libro");
        model.addAttribute("active", "libros");

        model.addAttribute("libros", libroService.listar());
        model.addAttribute("autores", autorService.listar());
        model.addAttribute("editoriales", editorialService.listar());
        model.addAttribute("areas", areaService.listar());

        model.addAttribute("nuevoLibro", libro);
        return "admin/libros";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        libroService.eliminar(id);
        return "redirect:/admin/libros";
    }
}
