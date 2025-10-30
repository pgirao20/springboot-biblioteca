package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Usuario;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

@Controller
@RequestMapping("/admin/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    //  Listar usuarios
        @GetMapping
        public String listarUsuarios(HttpSession session, Model model, 
                                    @RequestParam(required = false) String ok,
                                    @RequestParam(required = false) String warn,
                                    @RequestParam(required = false) String error) {

            //  Validar sesión
            Object user = session.getAttribute("usuarioLogeado");
            if (user == null) {
                return "redirect:/?error=Debe iniciar sesión para acceder al sistema.";
            }

            //  Solo ADMIN
            String rol = (String) session.getAttribute("rol");
            if (!"ADMIN".equalsIgnoreCase(rol)) {
                return "redirect:/admin/estudiantes?error=Acceso no autorizado.";
            }

            model.addAttribute("usuarios", usuarioService.listarTodos());
            model.addAttribute("nuevoUsuario", new Usuario());
            model.addAttribute("ok", ok);
            model.addAttribute("warn", warn);
            model.addAttribute("error", error);
            return "admin/usuarios";
        }


    // Guardar o actualizar usuario
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("nuevoUsuario") Usuario usuario,
                                 RedirectAttributes ra) {
        try {
            if (usuario.getId() == null) {
                // Registro nuevo
                Optional<Usuario> existente = usuarioService.buscarPorUsuario(usuario.getUsuario());
                if (existente.isPresent()) {
                    ra.addAttribute("error", "El nombre de usuario '" + usuario.getUsuario() + "' ya está registrado.");
                    return "redirect:/admin/usuarios";
                }
                usuarioService.guardar(usuario);
                ra.addAttribute("ok", "Usuario registrado correctamente.");
            } else {
                // Edición
                Optional<Usuario> duplicado = usuarioService.buscarPorUsuario(usuario.getUsuario());
                if (duplicado.isPresent() && !duplicado.get().getId().equals(usuario.getId())) {
                    ra.addAttribute("error", "El usuario '" + usuario.getUsuario() + "' ya pertenece a otro registro.");
                    return "redirect:/admin/usuarios";
                }
                usuarioService.guardar(usuario);
                ra.addAttribute("ok", "Usuario actualizado correctamente.");
            }
        } catch (Exception e) {
            ra.addAttribute("error", "Error al registrar o actualizar usuario.");
        }
        return "redirect:/admin/usuarios";
    }

    //  Editar usuario
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id,
                                HttpSession session,
                                Model model) {
        // Validación de acceso
        if (session.getAttribute("usuarioLogeado") == null) {
            return "redirect:/?error=Debe iniciar sesión.";
        }
        String rol = (String) session.getAttribute("rol");
        if (!"ADMIN".equalsIgnoreCase(rol)) {
            return "redirect:/admin/dashboard?error=Acceso no autorizado.";
        }

        Usuario usuario = usuarioService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID no válido: " + id));

        model.addAttribute("nuevoUsuario", usuario);
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "admin/usuarios";
    }

    // Eliminar usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes ra) {
        try {
            usuarioService.eliminar(id);
            ra.addAttribute("ok", "Usuario eliminado correctamente.");
        } catch (Exception e) {
            ra.addAttribute("error", "Error al eliminar el usuario.");
        }
        return "redirect:/admin/usuarios";
    }
}
