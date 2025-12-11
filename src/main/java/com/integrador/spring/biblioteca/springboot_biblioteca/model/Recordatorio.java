//src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/model/Recordatorio.java
package com.integrador.spring.biblioteca.springboot_biblioteca.model;
public class Recordatorio {
    private Long id;
    private String mensaje;
    private String frecuencia;

    public Recordatorio() {}

    public Recordatorio(Long id, String mensaje, String frecuencia) {
        this.id = id;
        this.mensaje = mensaje;
        this.frecuencia = frecuencia;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public String getFrecuencia() { return frecuencia; }
    public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }
}
