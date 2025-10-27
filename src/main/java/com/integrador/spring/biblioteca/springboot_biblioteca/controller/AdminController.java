package com.integrador.spring.biblioteca.springboot_biblioteca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String adminPanel(Model model) {
        model.addAttribute("titulo", "Panel Administrador");
        return "admin/usuarios"; // Página principal
    }


}
