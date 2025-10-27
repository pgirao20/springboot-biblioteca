package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Estudiante;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.IEstudianteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/estudiantes")
public class EstudianteController {

    private final IEstudianteService service;

    public EstudianteController(IEstudianteService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        List<String> grados = Arrays.asList("1ro Sec", "2do Sec", "3ro Sec", "4to Sec", "5to Sec");
        List<String> secciones = Arrays.asList("A", "B", "C", "D", "E");

        model.addAttribute("title", "Gestión de Estudiantes");
        model.addAttribute("active", "estudiantes");
        model.addAttribute("grados", grados);
        model.addAttribute("secciones", secciones);
        model.addAttribute("nuevoEstudiante", new Estudiante());
        model.addAttribute("estudiantes", service.listar());
        return "admin/estudiantes";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevoEstudiante") Estudiante estudiante) {
        service.guardar(estudiante);
        return "redirect:/admin/estudiantes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        var estudiante = service.buscarPorId(id).orElse(null);
        List<String> grados = Arrays.asList("1ro Sec", "2do Sec", "3ro Sec", "4to Sec", "5to Sec");
        List<String> secciones = Arrays.asList("A", "B", "C", "D", "E");

        model.addAttribute("title", "Editar Estudiante");
        model.addAttribute("active", "estudiantes");
        model.addAttribute("grados", grados);
        model.addAttribute("secciones", secciones);
        model.addAttribute("nuevoEstudiante", estudiante);
        model.addAttribute("estudiantes", service.listar());
        return "admin/estudiantes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/admin/estudiantes";
    }
}
