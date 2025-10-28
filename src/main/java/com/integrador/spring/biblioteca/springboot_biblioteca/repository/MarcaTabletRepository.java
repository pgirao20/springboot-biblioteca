package com.integrador.spring.biblioteca.springboot_biblioteca.repository;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.MarcaTablet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarcaTabletRepository extends JpaRepository<MarcaTablet, Long> {
    Optional<MarcaTablet> findByNombreIgnoreCase(String nombre);
    boolean existsByNombreIgnoreCase(String nombre);
}
