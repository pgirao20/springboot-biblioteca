// src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/service/impl/UsuarioServiceImpl.java
package com.integrador.spring.biblioteca.springboot_biblioteca.service.impl;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Usuario;
import com.integrador.spring.biblioteca.springboot_biblioteca.repository.UsuarioRepository;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.UsuarioService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> buscarPorUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }

@Override
    public Usuario guardar(Usuario usuario) {

        // EDICIÓN
        if (usuario.getId() != null) {
            Usuario actual = usuarioRepository.findById(usuario.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

            actual.setUsuario(usuario.getUsuario());
            actual.setNombre(usuario.getNombre());
            actual.setRol(usuario.getRol());

            // ✅ solo si escribe una nueva clave
            if (usuario.getClave() != null && !usuario.getClave().isBlank()) {
                actual.setClave(passwordEncoder.encode(usuario.getClave()));
            }

            return usuarioRepository.save(actual);
        }

        // NUEVO
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        return usuarioRepository.save(usuario);
    }


    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
