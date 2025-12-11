//src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/repository/TabletRepository.java
package com.integrador.spring.biblioteca.springboot_biblioteca.repository;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Tablet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TabletRepository extends JpaRepository<Tablet, Long> {
    
    Optional<Tablet> findBySn(String sn);  

    List<Tablet> findByEstado(String estado);
}
