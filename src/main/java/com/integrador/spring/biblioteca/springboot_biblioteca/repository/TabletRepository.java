package com.integrador.spring.biblioteca.springboot_biblioteca.repository;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Tablet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TabletRepository extends JpaRepository<Tablet, Long> {
    boolean existsBySn(String sn);
    Optional<Tablet> findBySn(String sn);
    List<Tablet> findBySnIn(Collection<String> sns);
}
