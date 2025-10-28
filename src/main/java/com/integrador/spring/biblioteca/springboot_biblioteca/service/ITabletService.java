package com.integrador.spring.biblioteca.springboot_biblioteca.service;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Tablet;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ITabletService {
    List<Tablet> listarTodos();
    Tablet guardar(Tablet tablet);
    Optional<Tablet> buscarPorId(Long id);
    void eliminar(Long id);
    boolean existeSn(String sn);

    /**
     * Guarda varias tablets en lote, retornando un resumen:
     *  - "creados": número de nuevas tablets creadas
     *  - "duplicados": lista de SN duplicados (no insertados)
     */
    Map<String, Object> guardarLote(Long marcaId, String modelo, Integer anioAdquisicion, List<String> sns,
                                    String estadoSiEdita); // estadoSiEdita puede ser null (por defecto Disponible)
}
