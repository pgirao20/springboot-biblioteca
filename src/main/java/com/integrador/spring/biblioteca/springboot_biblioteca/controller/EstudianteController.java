package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Estudiante;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.EstudianteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("estudiantes", estudianteService.listar());
        model.addAttribute("nuevoEstudiante", new Estudiante());
        agregarListas(model);
        return "admin/estudiantes";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Estudiante estudiante) {
        estudianteService.guardar(estudiante);
        return "redirect:/admin/estudiantes"; // Redirige para recargar la lista
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("nuevoEstudiante",
                estudianteService.buscarPorId(id).orElse(new Estudiante()));
        model.addAttribute("estudiantes", estudianteService.listar());
        agregarListas(model);
        return "admin/estudiantes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        estudianteService.eliminar(id);
        return "redirect:/admin/estudiantes";
    }

    // Listas dinámicas de grado y sección
    private void agregarListas(Model model) {
        List<String> grados = Arrays.asList("1ro Sec", "2do Sec", "3ro Sec", "4to Sec", "5to Sec");
        List<String> secciones = Arrays.asList("A", "B", "C", "D", "E");
        model.addAttribute("grados", grados);
        model.addAttribute("secciones", secciones);
    }
}
