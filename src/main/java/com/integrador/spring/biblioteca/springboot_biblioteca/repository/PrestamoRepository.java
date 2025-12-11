
//src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/repository/PrestamoRepository.java
package com.integrador.spring.biblioteca.springboot_biblioteca.repository;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.EstadoPrestamo;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.PrestamoEntity;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Estudiante;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<PrestamoEntity, Long> {

    List<PrestamoEntity> findByTabletIsNullOrderByFechaPrestamoDesc();
    boolean existsByLibroAndEstadoNot(Libro libro, EstadoPrestamo estadoDevuelto);
    List<PrestamoEntity> findByLibroAndEstadoNot(Libro libro, EstadoPrestamo estadoDevuelto);
    List<PrestamoEntity> findByEstudianteOrderByFechaPrestamoDesc(Estudiante estudiante);
    List<PrestamoEntity> findByEstadoOrderByFechaPrestamoDesc(EstadoPrestamo estado);
    List<PrestamoEntity> findByTabletIsNotNullOrderByFechaPrestamoDesc();
}
