package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Libro;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.LibroService;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.IAutorService;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.IEditorialService;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.IAreaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

import java.util.*;

@Controller
@RequestMapping("/admin/libros")
public class LibroController {

    @Autowired private LibroService libroService;
    @Autowired private IAutorService autorService;
    @Autowired private IEditorialService editorialService;
    @Autowired private IAreaService areaService;

    // Listar todos los libros
    @GetMapping
    public String listar(HttpSession session,
                         Model model,
                         @ModelAttribute("ok") String ok,
                         @ModelAttribute("warn") String warn,
                         @ModelAttribute("error") String error) {

        // Verificar sesión activa
        if (session.getAttribute("usuarioLogeado") == null) {
            return "redirect:/?error=Debe Loguear para acceder al sistema.";
        }

        model.addAttribute("libros", libroService.listarTodos());
        model.addAttribute("autores", autorService.listar());
        model.addAttribute("editoriales", editorialService.listar());
        model.addAttribute("areas", areaService.listar());
        model.addAttribute("nuevoLibro", new Libro());

        if (ok != null && !ok.isEmpty()) model.addAttribute("ok", ok);
        if (warn != null && !warn.isEmpty()) model.addAttribute("warn", warn);
        if (error != null && !error.isEmpty()) model.addAttribute("error", error);

        return "admin/libros";
    }

    // Limpiar formulario 
    @GetMapping("/limpiar")
    public String limpiar(HttpSession session) {
        if (session.getAttribute("usuarioLogeado") == null) {
            return "redirect:/?error=Debe iniciar Login.";
        }
        return "redirect:/admin/libros";
    }

    // Editar libro 
    @GetMapping("/editar/{id}")
    public String editar(HttpSession session, @PathVariable Long id, Model model) {
        if (session.getAttribute("usuarioLogeado") == null) {
            return "redirect:/?error=Debe iniciar Login.";
        }

        model.addAttribute("nuevoLibro", libroService.buscarPorId(id).orElse(new Libro()));
        model.addAttribute("libros", libroService.listarTodos());
        model.addAttribute("autores", autorService.listar());
        model.addAttribute("editoriales", editorialService.listar());
        model.addAttribute("areas", areaService.listar());
        return "admin/libros";
    }

    //Eliminar libro 
    @GetMapping("/eliminar/{id}")
    public String eliminar(HttpSession session, @PathVariable Long id, RedirectAttributes redirectAttrs) {
        if (session.getAttribute("usuarioLogeado") == null) {
            return "redirect:/?error=Debe iniciar Login.";
        }

        libroService.eliminar(id);
        redirectAttrs.addFlashAttribute("ok", "Libro eliminado correctamente.");
        return "redirect:/admin/libros";
    }

    // Guardar o actualizar
    @PostMapping("/guardar")
    public String guardar(HttpSession session,
                          @ModelAttribute("nuevoLibro") Libro libro,
                          @RequestParam(value = "snMultiple", required = false) String snMultiple,
                          RedirectAttributes redirectAttrs) {

        if (session.getAttribute("usuarioLogeado") == null) {
            return "redirect:/?error=Debe iniciar Login.";
        }

        try {
            // Alta múltiple
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
                    redirectAttrs.addFlashAttribute("warn",
                            "Se agregaron " + agregados.size() + " libro(s). SNs duplicados: " + String.join(", ", duplicados));
                } else {
                    redirectAttrs.addFlashAttribute("ok",
                            "Se agregaron " + agregados.size() + " libro(s) correctamente.");
                }

            } else {
                // Actualización o alta individual
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
}
