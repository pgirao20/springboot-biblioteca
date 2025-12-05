package com.integrador.spring.biblioteca.springboot_biblioteca.service;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Prestamo;

import java.time.LocalDate;
import java.util.List;

public interface PrestamoService {

    // Para mostrar en la tarjeta de "Préstamos registrados"
    List<Prestamo> listarPrestamosLibros();

    // Registrar varios libros (por SN) para un estudiante
    void registrarPrestamoLibros(String codigoEstudiante,
                                 LocalDate fechaPrestamo,
                                 LocalDate fechaDevolucion,
                                 List<String> sns);
        // NUEVOS PARA TABLETS
    List<Prestamo> listarPrestamosTablets();

    void registrarPrestamoTablets(String codigoEstudiante,
                                  LocalDate fechaPrestamo,
                                  LocalDate fechaDevolucion,
                                  List<String> snsTablets);    
    void eliminarPrestamoPorCodigo(String codigo);                    
}
