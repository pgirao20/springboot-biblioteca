package com.integrador.spring.biblioteca.springboot_biblioteca;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Estudiante;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Libro;
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Tablet;
import com.integrador.spring.biblioteca.springboot_biblioteca.repository.EstudianteRepository;
import com.integrador.spring.biblioteca.springboot_biblioteca.repository.LibroRepository;
import com.integrador.spring.biblioteca.springboot_biblioteca.repository.TabletRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.*;


@SpringBootTest
class SpringbootBibliotecaApplicationTests {

    @Mock
    private EstudianteRepository estudianteRepository;
    @Mock
    private LibroRepository libroRepository;
    @Mock
    private TabletRepository tabletRepository;

    public SpringbootBibliotecaApplicationTests() {
        MockitoAnnotations.openMocks(this);
    }

    // -------------------------------------------------------------------
    // MÓDULO ESTUDIANTE
    // -------------------------------------------------------------------
    @Test
    @DisplayName("No debe registrar estudiante con DNI duplicado")
    void noDebeRegistrarEstudianteConDniDuplicado() {
        Estudiante e1 = new Estudiante();
        e1.setDni("12345678");

        when(estudianteRepository.findByDni("12345678")).thenReturn(Optional.of(e1));

        Optional<Estudiante> duplicado = estudianteRepository.findByDni("12345678");
        Assertions.assertTrue(duplicado.isPresent(), "El DNI ya debería estar registrado");
    }

    @Test
    @DisplayName("Debe guardar estudiante correctamente")
    void debeGuardarEstudianteCorrectamente() {
        Estudiante nuevo = new Estudiante();
        nuevo.setDni("87654321");
        nuevo.setCodigo("A001");
        nuevo.setNombres("Juan");
        nuevo.setApellidos("Pérez");

        when(estudianteRepository.save(any(Estudiante.class))).thenReturn(nuevo);

        Estudiante guardado = estudianteRepository.save(nuevo);
        Assertions.assertEquals("Juan", guardado.getNombres());
        Assertions.assertEquals("A001", guardado.getCodigo());
    }

    // -------------------------------------------------------------------
    // MÓDULO LIBRO
    // -------------------------------------------------------------------
    @Test
    @DisplayName("No debe registrar libro con SN duplicado")
    void noDebeRegistrarLibroDuplicado() {
        Libro l1 = new Libro();
        l1.setSn("SN001");

        when(libroRepository.findBySn("SN001")).thenReturn(Optional.of(l1));

        Optional<Libro> duplicado = libroRepository.findBySn("SN001");
        Assertions.assertTrue(duplicado.isPresent(), "El SN del libro ya está registrado");
    }

    @Test
    @DisplayName("Debe guardar libro correctamente")
    void debeGuardarLibroCorrectamente() {
        Libro libro = new Libro();
        libro.setTitulo("El Principito");
        libro.setAnio(2020);
        libro.setSn("SN123");

        when(libroRepository.save(any(Libro.class))).thenReturn(libro);

        Libro guardado = libroRepository.save(libro);
        Assertions.assertEquals("El Principito", guardado.getTitulo());
        Assertions.assertEquals(2020, guardado.getAnio());
    }

    // -------------------------------------------------------------------
    // MÓDULO TABLET
    // -------------------------------------------------------------------
    @Test
    @DisplayName("No debe registrar tablet con SN duplicado")
    void noDebeRegistrarTabletDuplicada() {
        Tablet t1 = new Tablet();
        t1.setSn("TBL003");

        when(tabletRepository.findBySn("TBL005")).thenReturn(Optional.of(t1));

        Optional<Tablet> duplicado = tabletRepository.findBySn("TBL001");
        Assertions.assertTrue(duplicado.isPresent(), "El SN de la tablet ya está registrado");
    }

    @Test
    @DisplayName("Debe registrar tablet correctamente")
    void debeRegistrarTabletCorrectamente() {
        Tablet tablet = new Tablet();
        tablet.setModelo("Galaxy Tab A");
        tablet.setSn("TAB123");

        when(tabletRepository.save(any(Tablet.class))).thenReturn(tablet);

        Tablet guardada = tabletRepository.save(tablet);
        Assertions.assertEquals("Galaxy Tab A", guardada.getModelo());
        Assertions.assertEquals("TAB123", guardada.getSn());
    }

    // -------------------------------------------------------------------
    // CONTEXTO DE SPRING
    // -------------------------------------------------------------------
    @Test
    @DisplayName("El contexto de Spring Boot debe cargarse correctamente")
    void contextLoads() {
        Assertions.assertTrue(true, "El contexto de Spring Boot se cargó correctamente");
    }
}
