package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

//import com.integrador.spring.biblioteca.springboot_biblioteca.model.Usuario;
import com.integrador.spring.biblioteca.springboot_biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UsuarioRepository usuarioRepository;

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

        return usuarioRepository.findByUsuarioAndClave(usuario, clave)
                .map(user -> {
                    // Guardamos datos de sesión
                    session.setAttribute("usuarioLogeado", user);
                    session.setAttribute("nombre", user.getNombre());
                    session.setAttribute("rol", user.getRol());

                    // Redirige según el rol
                    if ("ADMIN".equalsIgnoreCase(user.getRol())) {
                        return "redirect:/admin/usuarios";
                    } else {
                        return "redirect:/admin/estudiantes";
                    }
                })
                .orElseGet(() -> {
                    model.addAttribute("error", "Usuario o contraseña incorrectos.Contáctese con el administrador");
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
