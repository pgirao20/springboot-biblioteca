// repository/LibroRepository.java
package com.integrador.spring.biblioteca.springboot_biblioteca.repository;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
public interface LibroRepository extends JpaRepository<Libro, Long> { }
