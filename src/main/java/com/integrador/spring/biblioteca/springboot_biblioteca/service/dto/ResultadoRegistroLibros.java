package com.integrador.spring.biblioteca.springboot_biblioteca.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class ResultadoRegistroLibros {
    private int agregados;
    private int duplicados;
    private List<String> snsDuplicados;
}
