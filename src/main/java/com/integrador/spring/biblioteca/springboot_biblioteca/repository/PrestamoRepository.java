//src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/repository/PrestamoRepository.java
package com.integrador.spring.biblioteca.springboot_biblioteca.repository;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.EstadoPrestamo;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.PrestamoEntity;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Estudiante;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Libro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<PrestamoEntity, Long> {

    // ====== tus métodos actuales ======
    List<PrestamoEntity> findByTabletIsNullOrderByFechaPrestamoDesc();
    boolean existsByLibroAndEstadoNot(Libro libro, EstadoPrestamo estadoDevuelto);
    List<PrestamoEntity> findByLibroAndEstadoNot(Libro libro, EstadoPrestamo estadoDevuelto);
    List<PrestamoEntity> findByEstudianteOrderByFechaPrestamoDesc(Estudiante estudiante);
    List<PrestamoEntity> findByEstadoOrderByFechaPrestamoDesc(EstadoPrestamo estado);
    List<PrestamoEntity> findByTabletIsNotNullOrderByFechaPrestamoDesc();

    // ==========================================================
    //  NUEVOS: SANCIONES (multas / costoReparacion)
    // ==========================================================

    // 1) Estudiantes que tienen algún préstamo con costoReparacion > 0
    @Query("""
        SELECT DISTINCT p.estudiante
        FROM PrestamoEntity p
        WHERE (p.costoReparacion IS NOT NULL AND p.costoReparacion > 0)
            OR (p.estudiante.sancionadoHasta IS NOT NULL)
        """)
    List<Estudiante> listarEstudiantesConSancionOMulta();

    // 2) Suma total de multa por estudiante
    @Query("""
           SELECT COALESCE(SUM(p.costoReparacion), 0)
           FROM PrestamoEntity p
           WHERE p.estudiante.id = :idEstudiante
           AND p.costoReparacion IS NOT NULL AND p.costoReparacion > 0
           """)
    BigDecimal calcularMontoTotalMulta(@Param("idEstudiante") Long idEstudiante);

    // 3) Inicio de sanción (aprox): última fechaDevolucionReal donde hubo multa
    @Query("""
           SELECT MAX(p.fechaDevolucionReal)
           FROM PrestamoEntity p
           WHERE p.estudiante.id = :idEstudiante
           AND p.costoReparacion IS NOT NULL AND p.costoReparacion > 0
           """)
    LocalDate obtenerUltimaFechaDevolucionConMulta(@Param("idEstudiante") Long idEstudiante);
}
