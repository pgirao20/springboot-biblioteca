package com.integrador.spring.biblioteca.springboot_biblioteca.service;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<Usuario> listar();
    void guardar(Usuario usuario);
    Optional<Usuario> buscarPorId(Long id);
    void eliminar(Long id);
}
