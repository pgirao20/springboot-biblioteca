package com.integrador.spring.biblioteca.springboot_biblioteca.service;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Usuario;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();

    public UsuarioService() {
        usuarios.add(new Usuario(1L, "Laura2u24", "Laura Castillo", "laucast@gmail.com", "AlaoCas"));
        usuarios.add(new Usuario(2L, "Juan2u24", "Juan Morales", "juan@gmail.com", "AJuanSie"));
        usuarios.add(new Usuario(3L, "Josue2u24", "Josue Mamani", "josue@gmail.com", "AJosueQui"));
    }

    public List<Usuario> listar() { return usuarios; }

    public void guardar(Usuario u) {
        if (u.getId() == null) {
            u.setId((long) (usuarios.size() + 1));
            usuarios.add(u);
        } else {
            usuarios.replaceAll(existing ->
                    existing.getId().equals(u.getId()) ? u : existing
            );
        }
    }

    public void eliminar(Long id) {
        usuarios.removeIf(u -> u.getId().equals(id));
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarios.stream().filter(u -> u.getId().equals(id)).findFirst();
    }
}
