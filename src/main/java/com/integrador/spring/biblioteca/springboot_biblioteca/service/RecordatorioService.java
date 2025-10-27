package com.integrador.spring.biblioteca.springboot_biblioteca.service;

import com.integrador.spring.biblioteca.springboot_biblioteca.model.Recordatorio;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecordatorioService {

    private final List<Recordatorio> recordatorios = new ArrayList<>();

    public RecordatorioService() {
        recordatorios.add(new Recordatorio(1L, "Se ha hecho el préstamo de un libro", "1 min"));
        recordatorios.add(new Recordatorio(2L, "Quedan pocos días para devolver", "3 días antes"));
        recordatorios.add(new Recordatorio(3L, "La fecha ya expiró", "24h después"));
    }

    public List<Recordatorio> listar() { return recordatorios; }

    public void guardar(Recordatorio r) {
        if (r.getId() == null) {
            r.setId((long) (recordatorios.size() + 1));
            recordatorios.add(r);
        } else {
            recordatorios.replaceAll(existing ->
                    existing.getId().equals(r.getId()) ? r : existing
            );
        }
    }

    public void eliminar(Long id) {
        recordatorios.removeIf(r -> r.getId().equals(id));
    }

    public Optional<Recordatorio> buscarPorId(Long id) {
        return recordatorios.stream().filter(r -> r.getId().equals(id)).findFirst();
    }
}
