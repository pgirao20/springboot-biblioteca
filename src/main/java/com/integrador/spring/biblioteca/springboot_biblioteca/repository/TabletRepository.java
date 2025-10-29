package com.integrador.spring.biblioteca.springboot_biblioteca.repository;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Tablet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TabletRepository extends JpaRepository<Tablet, Long> {
    Optional<Tablet> findBySn(String sn);
    boolean existsBySn(String sn);
}
