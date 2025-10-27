package com.integrador.spring.biblioteca.springboot_biblioteca.model;

public class Usuario {
    private Long id;
    private String usuario;
    private String nombre;
    private String correo;
    private String clave;

    public Usuario() {}

    public Usuario(Long id, String usuario, String nombre, String correo, String clave) {
        this.id = id;
        this.usuario = usuario;
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
}
