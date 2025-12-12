// src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/controller/LoginController.java
package com.integrador.spring.biblioteca.springboot_biblioteca.controller;
import com.integrador.spring.biblioteca.springboot_biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Página de inicio (login)
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(required = false) String ok,
                        @RequestParam(required = false) String error) {
        model.addAttribute("ok", ok);
        model.addAttribute("error", error);
        return "login/index";
    }

    // Procesar login
    @PostMapping("/login")
public String login(@RequestParam String usuario,
                    @RequestParam String clave,
                    HttpSession session,
                    Model model) {

    return usuarioRepository.findByUsuario(usuario)
            .map(user -> {

                if (!passwordEncoder.matches(clave, user.getClave())) {
                    model.addAttribute("error", "Usuario o contraseña incorrectos.Contactar con el administrador");
                    return "login/index";
                }

                session.setAttribute("usuarioLogeado", user);
                session.setAttribute("nombre", user.getNombre());
                session.setAttribute("rol", user.getRol());

                if ("ADMIN".equalsIgnoreCase(user.getRol())) {
                    return "redirect:/admin/usuarios";
                } else {
                    return "redirect:/admin/estudiantes";
                }
            })
            .orElseGet(() -> {
                model.addAttribute("error", "Usuario o contraseña incorrectos.Contactar con el administrador");
                return "login/index";
            });
}

    // Cerrar sesión
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/?ok=Logout correctamente.";
    }
}
