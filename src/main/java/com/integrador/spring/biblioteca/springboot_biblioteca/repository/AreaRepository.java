// repository/AreaRepository.java
package com.integrador.spring.biblioteca.springboot_biblioteca.repository;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AreaRepository extends JpaRepository<Area, Long> { }
