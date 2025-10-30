package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Estudiante;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import java.util.List;

@Controller
@RequestMapping("/admin/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private final EstudianteService estudianteService;

@GetMapping
public String listar(Model model,
                     @RequestParam(required = false) String ok,
                     @RequestParam(required = false) String warn,
                     @RequestParam(required = false) String error) {

    model.addAttribute("estudiantes", estudianteService.listarTodos());
    model.addAttribute("nuevoEstudiante", new Estudiante());

    // Listas fijas para desplegables
    model.addAttribute("grados", List.of(

            "1° Secundaria", "2° Secundaria", "3° Secundaria", 
            "4° Secundaria", "5° Secundaria"
    ));

    model.addAttribute("secciones", List.of("A", "B", "C", "D", "E"));

    model.addAttribute("ok", ok);
    model.addAttribute("warn", warn);
    model.addAttribute("error", error);

    return "admin/estudiantes";
}


    @PostMapping("/guardar")
public String guardar(@ModelAttribute("nuevoEstudiante") Estudiante base, RedirectAttributes ra) {
    try {
        // Depuración rápida
        System.out.println("Intentando guardar estudiante: " + base);

        //  EDICIÓN
        if (base.getId() != null) {
            Estudiante actual = estudianteService.buscarPorId(base.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));

            if (!base.getDni().equals(actual.getDni()) &&
                    estudianteService.buscarPorDni(base.getDni()).isPresent()) {
                ra.addAttribute("error", "El DNI ya está registrado en otro estudiante.");
                return "redirect:/admin/estudiantes/editar/" + base.getId();
            }

            if (!base.getCodigo().equals(actual.getCodigo()) &&
                    estudianteService.buscarPorCodigo(base.getCodigo()).isPresent()) {
                ra.addAttribute("error", "El código ya está registrado en otro estudiante.");
                return "redirect:/admin/estudiantes/editar/" + base.getId();
            }

            actual.setDni(base.getDni());
            actual.setCodigo(base.getCodigo());
            actual.setNombres(base.getNombres());
            actual.setApellidos(base.getApellidos());
            actual.setTelefono(base.getTelefono());
            actual.setCorreo(base.getCorreo());
            actual.setGrado(base.getGrado());
            actual.setSeccion(base.getSeccion());

            estudianteService.guardar(actual);
            ra.addAttribute("ok", "Estudiante actualizado correctamente.");
            return "redirect:/admin/estudiantes";
        }

        // NUEVO REGISTRO
        // Validaciones básicas
        if (base.getDni() == null || base.getDni().trim().isEmpty() ||
            base.getCodigo() == null || base.getCodigo().trim().isEmpty() ||
            base.getNombres() == null || base.getNombres().trim().isEmpty() ||
            base.getApellidos() == null || base.getApellidos().trim().isEmpty() ||
            base.getGrado() == null || base.getGrado().trim().isEmpty() ||
            base.getSeccion() == null || base.getSeccion().trim().isEmpty()) {

            ra.addAttribute("error", "Complete todos los campos obligatorios.");
            return "redirect:/admin/estudiantes";
        }

        // Validar duplicados
        if (estudianteService.buscarPorDni(base.getDni()).isPresent()) {
            ra.addAttribute("error", "El DNI ya está registrado.");
            return "redirect:/admin/estudiantes";
        }
        if (estudianteService.buscarPorCodigo(base.getCodigo()).isPresent()) {
            ra.addAttribute("error", "El código ya está registrado.");
            return "redirect:/admin/estudiantes";
        }

        // Guardar nuevo estudiante
        estudianteService.guardar(base);
        ra.addAttribute("ok", "Estudiante registrado correctamente.");
        return "redirect:/admin/estudiantes";

    } catch (Exception e) {
        e.printStackTrace(); 
        ra.addAttribute("error", "Error al registrar o actualizar estudiante.");
        return "redirect:/admin/estudiantes";
    }
}


@GetMapping("/editar/{id}")
public String editar(@PathVariable Long id, Model model) {
    Estudiante estudiante = estudianteService.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("ID no válido: " + id));

    model.addAttribute("nuevoEstudiante", estudiante);
    model.addAttribute("estudiantes", estudianteService.listarTodos());

    // 🔹 Reutilizamos las listas
    model.addAttribute("grados", List.of(
            "1° Secundaria", "2° Secundaria", "3° Secundaria",
            "4° Secundaria", "5° Secundaria"
    ));

    model.addAttribute("secciones", List.of("A", "B", "C", "D", "E"));

    return "admin/estudiantes";
}


    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        estudianteService.eliminar(id);
        return "redirect:/admin/estudiantes?ok=Estudiante eliminado correctamente.";
    }
}
