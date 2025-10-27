package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Usuario;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.IUsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/usuarios")
public class UsuarioController {

    private final IUsuarioService service;

    public UsuarioController(IUsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("title", "Administración de Usuarios");
        model.addAttribute("active", "usuarios");
        model.addAttribute("nuevoUsuario", new Usuario());
        model.addAttribute("usuarios", service.listar());
        return "admin/usuarios";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevoUsuario") Usuario usuario) {
        service.guardar(usuario);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        var usuario = service.buscarPorId(id).orElse(null);
        model.addAttribute("title", "Editar Usuario");
        model.addAttribute("active", "usuarios");
        model.addAttribute("nuevoUsuario", usuario);
        model.addAttribute("usuarios", service.listar());
        return "admin/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/admin/usuarios";
    }
}
