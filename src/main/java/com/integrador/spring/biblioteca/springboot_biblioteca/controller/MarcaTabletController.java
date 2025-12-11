// src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/controller/MarcaTabletController.java
package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.MarcaTablet;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.MarcaTabletService;
import com.integrador.spring.biblioteca.springboot_biblioteca.repository.MarcaTabletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/tablets/marcas")
@RequiredArgsConstructor
public class MarcaTabletController {

    private final MarcaTabletRepository marcaTabletRepository;
    private final MarcaTabletService marcaTabletService;

    @GetMapping
    public String listarMarcas(Model model,
                               @ModelAttribute(value = "ok") String ok,
                               @ModelAttribute(value = "error") String error) {

        model.addAttribute("marcas", marcaTabletService.listarTodas());
        model.addAttribute("nuevaMarca", new MarcaTablet());

        if (ok != null && !ok.isEmpty()) {
            model.addAttribute("ok", ok);
        }
        if (error != null && !error.isEmpty()) {
            model.addAttribute("error", error);
        }

        return "admin/marcas_tablet";
    }

    @PostMapping("/guardar")
    public String guardarMarca(@ModelAttribute("nuevaMarca") MarcaTablet marca,
                               RedirectAttributes redirectAttributes) {
        try {
            marcaTabletRepository.save(marca);
            redirectAttributes.addFlashAttribute("ok", "Marca registrada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar la marca.");
        }
        return "redirect:/admin/tablets/marcas";
    }

    @GetMapping("/editar/{id}")
    public String editarMarca(@PathVariable Long id, Model model) {
        MarcaTablet marca = marcaTabletRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Marca no encontrada"));

        model.addAttribute("nuevaMarca", marca);
        model.addAttribute("marcas", marcaTabletService.listarTodas());

        return "admin/marcas_tablet";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarMarca(@PathVariable Long id,
                                RedirectAttributes redirectAttributes) {
        try {
            marcaTabletRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("ok", "Marca eliminada correctamente.");
        } catch (DataIntegrityViolationException ex) {
            // Hay tablets que usan esta marca
            redirectAttributes.addFlashAttribute(
                    "error",
                    "No se puede eliminar la marca porque hay tablets asociadas a ella."
            );
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Ocurrió un error al intentar eliminar la marca."
            );
        }
        return "redirect:/admin/tablets/marcas";
    }
}
