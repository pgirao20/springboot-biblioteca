package com.integrador.spring.biblioteca.springboot_biblioteca.model;

public class Estudiante {

    private Long id;
    private String dni;
    private String codigo;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;
    private String grado;
    private String seccion;

    public Estudiante() {}

    public Estudiante(Long id, String dni, String codigo, String nombres, String apellidos,
                      String telefono, String correo, String grado, String seccion) {
        this.id = id;
        this.dni = dni;
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.grado = grado;
        this.seccion = seccion;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getGrado() { return grado; }
    public void setGrado(String grado) { this.grado = grado; }

    public String getSeccion() { return seccion; }
    public void setSeccion(String seccion) { this.seccion = seccion; }
}
