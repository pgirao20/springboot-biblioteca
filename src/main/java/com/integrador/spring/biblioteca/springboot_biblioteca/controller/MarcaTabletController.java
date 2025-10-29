package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.MarcaTablet;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.MarcaTabletService;
import com.integrador.spring.biblioteca.springboot_biblioteca.repository.MarcaTabletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/tablets/marcas")
@RequiredArgsConstructor
public class MarcaTabletController {

    private final MarcaTabletRepository marcaTabletRepository;
    private final MarcaTabletService marcaTabletService;

    @GetMapping
    public String listarMarcas(Model model,
                               @RequestParam(required = false) String ok,
                               @RequestParam(required = false) String error) {
        model.addAttribute("marcas", marcaTabletService.listarTodas());
        model.addAttribute("nuevaMarca", new MarcaTablet());
        model.addAttribute("ok", ok);
        model.addAttribute("error", error);
        return "admin/marcas_tablet";
    }

    @PostMapping("/guardar")
    public String guardarMarca(@ModelAttribute("nuevaMarca") MarcaTablet marca) {
        try {
            marcaTabletRepository.save(marca);
            return "redirect:/admin/tablets/marcas?ok=Marca registrada correctamente.";
        } catch (Exception e) {
            return "redirect:/admin/tablets/marcas?error=Error al registrar la marca.";
        }
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
    public String eliminarMarca(@PathVariable Long id) {
        marcaTabletRepository.deleteById(id);
        return "redirect:/admin/tablets/marcas?ok=Marca eliminada correctamente.";
    }
}
