package com.integrador.spring.biblioteca.springboot_biblioteca.repository;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findBySn(String sn);
    boolean existsBySn(String sn);
}
