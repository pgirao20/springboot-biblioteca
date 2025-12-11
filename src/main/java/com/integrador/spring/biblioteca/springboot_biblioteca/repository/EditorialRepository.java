//src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/repository/EditorialRepository.java
package com.integrador.spring.biblioteca.springboot_biblioteca.repository;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EditorialRepository extends JpaRepository<Editorial, Long> { }
