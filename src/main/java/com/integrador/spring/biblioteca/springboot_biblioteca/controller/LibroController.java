package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.*;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/admin/libros")
@RequiredArgsConstructor
public class LibroController {

    private final LibroService libroService;
    private final IAutorService autorService;
    private final IEditorialService editorialService;
    private final IAreaService areaService;

   
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("libros", libroService.listarTodos());
        model.addAttribute("autores", autorService.listar());
        model.addAttribute("editoriales", editorialService.listar());
        model.addAttribute("areas", areaService.listar());
        model.addAttribute("nuevoLibro", new Libro());
        return "admin/libros";
    }


   @PostMapping("/guardar")
public String guardar(
        @ModelAttribute("nuevoLibro") Libro libro,
        @RequestParam(value = "snMultiple", required = false) String snMultiple,
        RedirectAttributes redirectAttrs) {

    try {
        // 🟦 Caso 1: Alta múltiple (textarea)
        if (libro.getId() == null && snMultiple != null && !snMultiple.trim().isEmpty()) {
            String[] sns = snMultiple.split("\\r?\\n");
            List<String> duplicados = new ArrayList<>();
            List<String> agregados = new ArrayList<>();

            for (String sn : sns) {
                String serie = sn.trim();
                if (serie.isEmpty()) continue;

                Optional<Libro> existente = libroService.buscarPorSn(serie);
                if (existente.isPresent()) {
                    duplicados.add(serie);
                } else {
                    Libro nuevo = new Libro();
                    nuevo.setTitulo(libro.getTitulo());
                    nuevo.setAnio(libro.getAnio());
                    nuevo.setAutor(libro.getAutor());
                    nuevo.setEditorial(libro.getEditorial());
                    nuevo.setArea(libro.getArea());
                    nuevo.setSn(serie);
                    libroService.guardar(nuevo);
                    agregados.add(serie);
                }
            }

            if (!duplicados.isEmpty()) {
                redirectAttrs.addFlashAttribute("warn", "Se agregaron " + agregados.size() +
                        " libros nuevos. Los siguientes SN ya existían: " + String.join(", ", duplicados));
            } else {
                redirectAttrs.addFlashAttribute("ok", "Se agregaron " + agregados.size() + " libros correctamente.");
            }

        } else {

            Optional<Libro> existente = libroService.buscarPorSn(libro.getSn());

            if (existente.isPresent() && !existente.get().getId().equals(libro.getId())) {
                redirectAttrs.addFlashAttribute("error",
                        "El SN '" + libro.getSn() + "' ya está asignado a otro libro.");
            } else {
                libroService.guardar(libro);
                redirectAttrs.addFlashAttribute("ok", "Libro guardado correctamente.");
            }
        }

    } catch (Exception e) {
        redirectAttrs.addFlashAttribute("error", "Error al guardar el libro: " + e.getMessage());
    }


    return "redirect:/admin/libros";
}


   
 @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Optional<Libro> libroOpt = libroService.buscarPorId(id);

        if (libroOpt.isPresent()) {
            model.addAttribute("nuevoLibro", libroOpt.get());
        } else {
            model.addAttribute("nuevoLibro", new Libro());
            model.addAttribute("error", "Libro no encontrado.");
        }

        model.addAttribute("libros", libroService.listarTodos());
        model.addAttribute("autores", autorService.listar());
        model.addAttribute("editoriales", editorialService.listar());
        model.addAttribute("areas", areaService.listar());
        return "admin/libros";
    }


  
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        libroService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "🗑️ Libro eliminado correctamente.");
        return "redirect:/admin/libros";
    }

 
    @GetMapping("/limpiar")
    public String limpiar() {
        return "redirect:/admin/libros";
    }
}
