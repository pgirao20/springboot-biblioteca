package com.integrador.spring.biblioteca.springboot_biblioteca.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class ResultadoRegistroTablets {
    private int agregadas;
    private int duplicadas;
    private List<String> snsDuplicadas;
}
