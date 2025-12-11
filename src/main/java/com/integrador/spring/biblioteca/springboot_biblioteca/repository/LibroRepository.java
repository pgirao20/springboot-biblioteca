//src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/repository/LibroRepository.java
package com.integrador.spring.biblioteca.springboot_biblioteca.repository;
<<<<<<< HEAD

=======
>>>>>>> 8a693b5 (prestamos v2)
import java.util.List;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findBySn(String sn);
    boolean existsBySn(String sn);
<<<<<<< HEAD
    // NUEVO: listar solo los libros con estado = 'DISPONIBLE'
=======
   
>>>>>>> 8a693b5 (prestamos v2)
    List<Libro> findByEstado(String estado);
    
}
