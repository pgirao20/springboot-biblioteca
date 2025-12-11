<<<<<<< HEAD
package com.integrador.spring.biblioteca.springboot_biblioteca.repository;

=======
//src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/repository/PrestamoRepository.java
package com.integrador.spring.biblioteca.springboot_biblioteca.repository;
>>>>>>> 8a693b5 (prestamos v2)
import com.integrador.spring.biblioteca.springboot_biblioteca.model.EstadoPrestamo;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.PrestamoEntity;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Estudiante;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
<<<<<<< HEAD

=======
>>>>>>> 8a693b5 (prestamos v2)
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<PrestamoEntity, Long> {

<<<<<<< HEAD
    /**
     * 🔹 Listar préstamos de LIBROS
     *    (tablet == null => es préstamo de libro)
     */
    List<PrestamoEntity> findByTabletIsNullOrderByFechaPrestamoDesc();

    /**
     * 🔹 Verificar si un libro (por entidad Libro) tiene algún préstamo
     *    con estado distinto a DEVUELTO.
     *    -> Si true => ese SN NO está disponible.
     */
    boolean existsByLibroAndEstadoNot(Libro libro, EstadoPrestamo estadoDevuelto);

    /**
     * 🔹 Obtener todos los préstamos activos de un libro
     *    (opcional, útil para debug o reportes)
     */
    List<PrestamoEntity> findByLibroAndEstadoNot(Libro libro, EstadoPrestamo estadoDevuelto);

    /**
     * 🔹 Listar préstamos de un estudiante (por entidad Estudiante)
     */
    List<PrestamoEntity> findByEstudianteOrderByFechaPrestamoDesc(Estudiante estudiante);

    /**
     * 🔹 Listar préstamos por estado (ACTIVO, PENDIENTE, VENCIDO, DEVUELTO)
     */
    List<PrestamoEntity> findByEstadoOrderByFechaPrestamoDesc(EstadoPrestamo estado);

=======
    List<PrestamoEntity> findByTabletIsNullOrderByFechaPrestamoDesc();
    boolean existsByLibroAndEstadoNot(Libro libro, EstadoPrestamo estadoDevuelto);
    List<PrestamoEntity> findByLibroAndEstadoNot(Libro libro, EstadoPrestamo estadoDevuelto);
    List<PrestamoEntity> findByEstudianteOrderByFechaPrestamoDesc(Estudiante estudiante);
    List<PrestamoEntity> findByEstadoOrderByFechaPrestamoDesc(EstadoPrestamo estado);
>>>>>>> 8a693b5 (prestamos v2)
    List<PrestamoEntity> findByTabletIsNotNullOrderByFechaPrestamoDesc();
}
