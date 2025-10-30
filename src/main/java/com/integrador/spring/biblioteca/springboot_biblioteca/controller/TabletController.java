package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Tablet;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.MarcaTabletService;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.TabletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/tablets")
@RequiredArgsConstructor
public class TabletController {

    private final TabletService tabletService;
    private final MarcaTabletService marcaTabletService;

    //  Mostrar página principal
    @GetMapping
    public String listarTablets(HttpSession session,
                                Model model,
                                @RequestParam(required = false) String ok,
                                @RequestParam(required = false) String warn,
                                @RequestParam(required = false) String error) {

        //  Verificar sesión activa
        if (session.getAttribute("usuarioLogeado") == null) {
            return "redirect:/?error=Debe Loguear para acceder al sistema.";
        }

        model.addAttribute("tablets", tabletService.listarTodos());
        model.addAttribute("marcas", marcaTabletService.listarTodas());
        model.addAttribute("nuevaTablet", new Tablet());
        model.addAttribute("ok", ok);
        model.addAttribute("warn", warn);
        model.addAttribute("error", error);
        return "admin/tablets";
    }

    //  Guardar o actualizar
    @PostMapping("/guardar")
    public String guardarTablet(HttpSession session,
                                @ModelAttribute("nuevaTablet") Tablet base,
                                @RequestParam(required = false, name = "snsMultiple") String snsMultiple,
                                RedirectAttributes ra) {

        // Verificar sesión activa
        if (session.getAttribute("usuarioLogeado") == null) {
            return "redirect:/?error=Debe iniciar Login.";
        }

        try {
            // CASO 1: EDICIÓN
            if (base.getId() != null) {
                Tablet existente = tabletService.buscarPorId(base.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Tablet no encontrada"));

                if (!base.getSn().equalsIgnoreCase(existente.getSn())
                        && tabletService.buscarPorSn(base.getSn()).isPresent()) {
                    ra.addAttribute("error", "El número de serie ya existe en otra tablet.");
                    return "redirect:/admin/tablets/editar/" + base.getId();
                }

                existente.setMarca(base.getMarca());
                existente.setModelo(base.getModelo());
                existente.setAnioAdquisicion(base.getAnioAdquisicion());
                existente.setEstado(base.getEstado());
                existente.setSn(base.getSn().trim());

                tabletService.guardar(existente);
                ra.addAttribute("ok", "Tablet actualizada correctamente.");
                return "redirect:/admin/tablets";
            }

            // CASO 2: REGISTRO NUEVO
            int creadas = 0;
            List<String> duplicadas = new ArrayList<>();
            List<String> invalidas = new ArrayList<>();

            if (snsMultiple != null && !snsMultiple.trim().isEmpty()) {
                String[] partes = snsMultiple.split("[,\\r?\\n]+");
                for (String raw : partes) {
                    String sn = raw == null ? "" : raw.trim();
                    if (sn.isEmpty()) {
                        invalidas.add("(vacío)");
                        continue;
                    }

                    if (tabletService.buscarPorSn(sn).isPresent()) {
                        duplicadas.add(sn);
                        continue;
                    }

                    Tablet nueva = new Tablet();
                    nueva.setMarca(base.getMarca());
                    nueva.setModelo(base.getModelo());
                    nueva.setAnioAdquisicion(base.getAnioAdquisicion());
                    nueva.setEstado("Disponible");
                    nueva.setSn(sn);
                    tabletService.guardar(nueva);
                    creadas++;
                }

            } else {
                String sn = base.getSn() == null ? "" : base.getSn().trim();
                if (sn.isEmpty()) {
                    ra.addAttribute("error", "Ingrese un SN o una lista de SNs para registrar.");
                    return "redirect:/admin/tablets";
                }

                if (tabletService.buscarPorSn(sn).isPresent()) {
                    duplicadas.add(sn);
                } else {
                    base.setSn(sn);
                    base.setEstado("Disponible");
                    tabletService.guardar(base);
                    creadas++;
                }
            }

            // Mensajes
            if (creadas > 0)
                ra.addAttribute("ok", "Se registraron " + creadas + " tablet(s).");

            if (!duplicadas.isEmpty() || !invalidas.isEmpty()) {
                int max = Math.min(duplicadas.size(), 10);
                String dupPreview = String.join(", ", duplicadas.subList(0, max));
                String dupSufijo = duplicadas.size() > max ? " …(+ " + (duplicadas.size() - max) + " más)" : "";

                StringBuilder warn = new StringBuilder();
                if (!duplicadas.isEmpty()) {
                    warn.append("SN duplicadas (").append(duplicadas.size()).append("): ")
                            .append(dupPreview).append(dupSufijo).append(". ");
                }
                if (!invalidas.isEmpty()) {
                    warn.append("SN inválidas/vacías (").append(invalidas.size()).append(").");
                }
                ra.addAttribute("warn", warn.toString().trim());
            }

            if (creadas == 0) {
                if (!duplicadas.isEmpty())
                    ra.addAttribute("error", "No se registró ninguna tablet porque todas las SN estaban duplicadas.");
                else if (!invalidas.isEmpty())
                    ra.addAttribute("error", "No se registró ninguna tablet por SN inválidas o vacías.");
                else
                    ra.addAttribute("error", "No se registró ninguna tablet.");
            }

            return "redirect:/admin/tablets";

        } catch (Exception e) {
            e.printStackTrace();
            ra.addAttribute("error", "Error al registrar o actualizar tablet.");
            return "redirect:/admin/tablets";
        }
    }

    //  Editar
    @GetMapping("/editar/{id}")
    public String editarTablet(HttpSession session,
                               @PathVariable Long id,
                               Model model) {

        //  Verificar sesión
        if (session.getAttribute("usuarioLogeado") == null) {
            return "redirect:/?error=Debe iniciar Login.";
        }

        Tablet tablet = tabletService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID no válido: " + id));

        model.addAttribute("nuevaTablet", tablet);
        model.addAttribute("marcas", marcaTabletService.listarTodas());
        model.addAttribute("tablets", tabletService.listarTodos());
        return "admin/tablets";
    }

    //  Eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminarTablet(HttpSession session,
                                 @PathVariable Long id,
                                 RedirectAttributes ra) {

        //  Verificar sesión
        if (session.getAttribute("usuarioLogeado") == null) {
            return "redirect:/?error=Debe iniciar Login.";
        }

        try {
            tabletService.eliminar(id);
            ra.addAttribute("ok", "Tablet eliminada correctamente.");
        } catch (Exception e) {
            ra.addAttribute("error", "Error al eliminar tablet.");
        }

        return "redirect:/admin/tablets";
    }
}
