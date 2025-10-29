package com.integrador.spring.biblioteca.springboot_biblioteca.controller;


import com.integrador.spring.biblioteca.springboot_biblioteca.model.Tablet;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.MarcaTabletService;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.TabletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/admin/tablets")
@RequiredArgsConstructor
public class TabletController {

    private final TabletService tabletService;
    private final MarcaTabletService marcaTabletService;

    @GetMapping
    public String listarTablets(Model model,
                                @RequestParam(required = false) String ok,
                                @RequestParam(required = false) String error) {
        model.addAttribute("tablets", tabletService.listarTodos());
        model.addAttribute("marcas", marcaTabletService.listarTodas());
        model.addAttribute("nuevaTablet", new Tablet());
        model.addAttribute("ok", ok);
        model.addAttribute("error", error);
        return "admin/tablets";
    }

    @PostMapping("/guardar")
    public String guardarTablet(@ModelAttribute("nuevaTablet") Tablet tablet, Model model) {
        try {
            tabletService.guardar(tablet);
            return "redirect:/admin/tablets?ok=Tablet registrada correctamente.";
        } catch (IllegalStateException e) {
            return "redirect:/admin/tablets?error=" + e.getMessage();
        } catch (Exception e) {
            return "redirect:/admin/tablets?error=Error al registrar tablet.";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarTablet(@PathVariable Long id, Model model) {
        Tablet tablet = tabletService.obtenerPorId(id);
        model.addAttribute("nuevaTablet", tablet);
        model.addAttribute("marcas", marcaTabletService.listarTodas());
        model.addAttribute("tablets", tabletService.listarTodos());
        return "admin/tablets";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarTablet(@PathVariable Long id) {
        tabletService.eliminar(id);
        return "redirect:/admin/tablets?ok=Tablet eliminada correctamente.";
    }
}
