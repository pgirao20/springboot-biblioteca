//src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/repository/TabletRepository.java
package com.integrador.spring.biblioteca.springboot_biblioteca.repository;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Tablet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
<<<<<<< HEAD

=======
>>>>>>> 8a693b5 (prestamos v2)
import java.util.List;
import java.util.Optional;

@Repository
public interface TabletRepository extends JpaRepository<Tablet, Long> {

<<<<<<< HEAD
    // Ya existente (o similar)
    Optional<Tablet> findBySn(String sn);   // o findBySerie, ajusta al nombre real

    // NUEVO: solo tablets en cierto estado (DISPONIBLE, PRESTADO, etc.)
=======
    
    Optional<Tablet> findBySn(String sn);  

>>>>>>> 8a693b5 (prestamos v2)
    List<Tablet> findByEstado(String estado);
}
