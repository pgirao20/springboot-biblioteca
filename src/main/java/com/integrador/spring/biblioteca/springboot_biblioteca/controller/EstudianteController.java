// src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/controller/EstudianteController.java
package com.integrador.spring.biblioteca.springboot_biblioteca.controller;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Estudiante;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Controller
@RequestMapping("/admin/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private final EstudianteService estudianteService;

    @GetMapping
    public String listar(HttpSession session,
                         Model model,
                         @RequestParam(required = false) String ok,
                         @RequestParam(required = false) String warn,
                         @RequestParam(required = false) String error) {

        if (session.getAttribute("usuarioLogeado") == null) {
            return "redirect:/?error=Debe iniciar Login para acceder al sistema.";
        }

        model.addAttribute("estudiantes", estudianteService.listarTodos());
        model.addAttribute("nuevoEstudiante", new Estudiante());

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
public String guardar(HttpSession session,
                      @ModelAttribute("nuevoEstudiante") Estudiante base,
                      RedirectAttributes ra) {

    if (session.getAttribute("usuarioLogeado") == null) {
        return "redirect:/?error=Debe iniciar Login.";
    }

    try {
        // CASO: EDICIÓN
        if (base.getId() != null) {
            Estudiante actual = estudianteService.buscarPorId(base.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado."));

            if (!base.getDni().equals(actual.getDni()) &&
                    estudianteService.buscarPorDni(base.getDni()).isPresent()) {
                ra.addAttribute("error", "El DNI '" + base.getDni() + "' ya está registrado en otro estudiante.");
                return "redirect:/admin/estudiantes/editar/" + base.getId();
            }

            if (!base.getCodigo().equals(actual.getCodigo()) &&
                    estudianteService.buscarPorCodigo(base.getCodigo()).isPresent()) {
                ra.addAttribute("error", "El código '" + base.getCodigo() + "' ya está registrado en otro estudiante.");
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
            ra.addAttribute("ok", "[EDITAR] Estudiante actualizado correctamente.");
            return "redirect:/admin/estudiantes";
        }

        // CASO: NUEVO REGISTRO
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
            ra.addAttribute("error", "El DNI '" + base.getDni() + "' ya está registrado en otro estudiante.");
            return "redirect:/admin/estudiantes";
        }
        if (estudianteService.buscarPorCodigo(base.getCodigo()).isPresent()) {
            ra.addAttribute("error", "El código '" + base.getCodigo() + "' ya está registrado en otro estudiante.");
            return "redirect:/admin/estudiantes";
        }

        // Guardar nuevo estudiante
        estudianteService.guardar(base);
        ra.addAttribute("ok", "[GUARDAR] Estudiante registrado correctamente.");
        return "redirect:/admin/estudiantes";

    } catch (Exception e) {
        e.printStackTrace();
        ra.addAttribute("error", "Error al registrar o actualizar estudiante.");
        return "redirect:/admin/estudiantes";
    }
}


    @GetMapping("/editar/{id}")
    public String editar(HttpSession session, @PathVariable Long id, Model model) {
        if (session.getAttribute("usuarioLogeado") == null) {
            return "redirect:/?error=Debe iniciar Login.";
        }

        Estudiante estudiante = estudianteService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID no válido: " + id));

        model.addAttribute("nuevoEstudiante", estudiante);
        model.addAttribute("estudiantes", estudianteService.listarTodos());

        model.addAttribute("grados", List.of(
                "1° Secundaria", "2° Secundaria", "3° Secundaria",
                "4° Secundaria", "5° Secundaria"
        ));

        model.addAttribute("secciones", List.of("A", "B", "C", "D", "E"));

        return "admin/estudiantes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(HttpSession session, @PathVariable Long id, RedirectAttributes ra) {
        if (session.getAttribute("usuarioLogeado") == null) {
            return "redirect:/?error=Debe iniciar Login.";
        }

        try {
            estudianteService.eliminar(id);
            ra.addAttribute("ok", "Estudiante eliminado correctamente.");
        } catch (Exception e) {
            ra.addAttribute("error", "Error al eliminar estudiante.");
        }

        return "redirect:/admin/estudiantes";
    }
        // ============================================
    //  API JSON: Verificar sanción por código
    // ============================================
    @GetMapping("/sancion/{codigo}")
    @ResponseBody
    public Map<String, Object> verificarSancion(@PathVariable("codigo") String codigo) {

        Map<String, Object> resp = new HashMap<>();

        var optEst = estudianteService.buscarPorCodigo(codigo);
        if (optEst.isEmpty()) {
            resp.put("existe", false);
            return resp;
        }

        Estudiante est = optEst.get();
        resp.put("existe", true);
        resp.put("codigo", est.getCodigo());
        resp.put("nombres", est.getNombres());
        resp.put("apellidos", est.getApellidos());

        LocalDate hoy = LocalDate.now();
        LocalDate sancionadoHasta = est.getSancionadoHasta();

        boolean sancionado = sancionadoHasta != null && !sancionadoHasta.isBefore(hoy);

        resp.put("sancionado", sancionado);
        resp.put("sancionadoHasta", sancionadoHasta); // se serializa como "2025-12-11"

        return resp;
    }

}
