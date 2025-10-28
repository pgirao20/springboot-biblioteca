package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.MarcaTablet;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Tablet;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.IMarcaTabletService;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.ITabletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/tablets")
public class TabletController {

    private final ITabletService tabletService;
    private final IMarcaTabletService marcaService;

    // -------- Registro (tabla + formulario) --------
    @GetMapping
    public String listarTablets(Model model) {
        model.addAttribute("title", "Gestión de Tablets");
        model.addAttribute("active", "tablets");

        model.addAttribute("tablets", tabletService.listarTodos());
        model.addAttribute("marcas", marcaService.listarTodos());

        // Formulario registrar/editar
        if (!model.containsAttribute("nuevaTablet")) {
            model.addAttribute("nuevaTablet", new Tablet());
        }

        // Soporta alta múltiple (textarea de SNs)
        if (!model.containsAttribute("snLote")) {
            model.addAttribute("snLote", "");
        }

        return "admin/tablets";
    }

    // Guardar una tablet individual (crear/editar)
    @PostMapping("/guardar")
    public String guardarTablet(@ModelAttribute("nuevaTablet") Tablet tablet,
                                RedirectAttributes ra) {
        try {
            tabletService.guardar(tablet);
            ra.addFlashAttribute("ok", "Tablet guardada correctamente.");
        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            ra.addFlashAttribute("nuevaTablet", tablet);
        }
        return "redirect:/admin/tablets";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, RedirectAttributes ra) {
        Optional<Tablet> opt = tabletService.buscarPorId(id);
        if (opt.isEmpty()) {
            ra.addFlashAttribute("error", "Tablet no encontrada.");
            return "redirect:/admin/tablets";
        }
        ra.addFlashAttribute("nuevaTablet", opt.get());
        return "redirect:/admin/tablets";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        tabletService.eliminar(id);
        ra.addFlashAttribute("ok", "Tablet eliminada.");
        return "redirect:/admin/tablets";
    }

    // -------- Alta múltiple por lista de SN (textarea) --------
    @PostMapping("/guardar-lote")
    public String guardarLote(@RequestParam("marcaId") Long marcaId,
                              @RequestParam("modelo") String modelo,
                              @RequestParam("anioAdquisicion") Integer anioAdquisicion,
                              @RequestParam("sns") String snsTextarea,
                              RedirectAttributes ra) {

        List<String> sns = Arrays.stream(snsTextarea.split("\\r?\\n"))
                .map(String::trim)
                .filter(StringUtils::hasText)
                .toList();

        Map<String, Object> res = tabletService.guardarLote(marcaId, modelo, anioAdquisicion, sns, null);
        int creados = (int) res.get("creados");
        @SuppressWarnings("unchecked")
        List<String> duplicados = (List<String>) res.get("duplicados");

        if (creados > 0) {
            ra.addFlashAttribute("ok", "Se agregaron " + creados + " nuevas tablets.");
        }
        if (!duplicados.isEmpty()) {
            ra.addFlashAttribute("warn",
                    "No se agregaron " + duplicados.size() + " por duplicado. SN duplicados: " + String.join(", ", duplicados));
        }

        return "redirect:/admin/tablets";
    }

    // -------- Navegación Marcas --------
    @GetMapping("/marcas")
    public String marcas(Model model) {
        model.addAttribute("title", "Marcas de Tablets");
        model.addAttribute("active", "tablets");
        model.addAttribute("marcas", marcaService.listarTodos());
        if (!model.containsAttribute("nuevaMarca")) {
            model.addAttribute("nuevaMarca", new MarcaTablet());
        }
        return "admin/marcas_tablet";
    }

    @PostMapping("/marcas/guardar")
    public String guardarMarca(@ModelAttribute("nuevaMarca") MarcaTablet marca,
                               RedirectAttributes ra) {
        if (marca.getNombre() == null || marca.getNombre().isBlank()) {
            ra.addFlashAttribute("error", "El nombre de la marca es obligatorio.");
            ra.addFlashAttribute("nuevaMarca", marca);
            return "redirect:/admin/tablets/marcas";
        }
        if (marca.getId() == null && marcaService.existePorNombre(marca.getNombre())) {
            ra.addFlashAttribute("error", "La marca '" + marca.getNombre() + "' ya existe.");
            ra.addFlashAttribute("nuevaMarca", marca);
            return "redirect:/admin/tablets/marcas";
        }
        marcaService.guardar(marca);
        ra.addFlashAttribute("ok", "Marca guardada correctamente.");
        return "redirect:/admin/tablets/marcas";
    }

    @GetMapping("/marcas/editar/{id}")
    public String editarMarca(@PathVariable Long id, RedirectAttributes ra) {
        Optional<MarcaTablet> opt = marcaService.buscarPorId(id);
        if (opt.isEmpty()) {
            ra.addFlashAttribute("error", "Marca no encontrada.");
            return "redirect:/admin/tablets/marcas";
        }
        ra.addFlashAttribute("nuevaMarca", opt.get());
        return "redirect:/admin/tablets/marcas";
    }

    @GetMapping("/marcas/eliminar/{id}")
    public String eliminarMarca(@PathVariable Long id, RedirectAttributes ra) {
        marcaService.eliminar(id);
        ra.addFlashAttribute("ok", "Marca eliminada.");
        return "redirect:/admin/tablets/marcas";
    }
}
