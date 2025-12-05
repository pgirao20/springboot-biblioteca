package com.integrador.spring.biblioteca.springboot_biblioteca.repository;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Tablet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TabletRepository extends JpaRepository<Tablet, Long> {

    // Ya existente (o similar)
    Optional<Tablet> findBySn(String sn);   // o findBySerie, ajusta al nombre real

    // NUEVO: solo tablets en cierto estado (DISPONIBLE, PRESTADO, etc.)
    List<Tablet> findByEstado(String estado);
}
