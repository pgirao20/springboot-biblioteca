//src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/controller/RecordatorioController.java
package com.integrador.spring.biblioteca.springboot_biblioteca.controller;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Recordatorio;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.RecordatorioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/recordatorios")
public class RecordatorioController {

    private final RecordatorioService recordatorioService;

    public RecordatorioController(RecordatorioService recordatorioService) {
        this.recordatorioService = recordatorioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("recordatorios", recordatorioService.listar());
        model.addAttribute("nuevoRecordatorio", new Recordatorio());
        return "admin/recordatorios";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Recordatorio recordatorio) {
        recordatorioService.guardar(recordatorio);
        return "redirect:/admin/recordatorios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        recordatorioService.eliminar(id);
        return "redirect:/admin/recordatorios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("recordatorios", recordatorioService.listar());
        model.addAttribute("nuevoRecordatorio", recordatorioService.buscarPorId(id).orElse(new Recordatorio()));
        return "admin/recordatorios";
    }
}
