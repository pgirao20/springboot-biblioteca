// repository/AutorRepository.java
package com.integrador.spring.biblioteca.springboot_biblioteca.repository;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AutorRepository extends JpaRepository<Autor, Long> { }
