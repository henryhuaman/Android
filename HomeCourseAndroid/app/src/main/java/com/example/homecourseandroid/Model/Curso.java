package com.example.homecourseandroid.Model;

public class Curso {
    private String Id;
    private String Nombre;
    private String Descripcion;
    private String Categoria;
    private String ProfesorId;
    private double precio;

    public Curso(String id, String nombre, String descripcion, String categoria, String profesorId, double precio) {
        Id = id;
        Nombre = nombre;
        Descripcion = descripcion;
        Categoria = categoria;
        ProfesorId = profesorId;
        this.precio = precio;
    }
    public Curso() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getProfesorId() {
        return ProfesorId;
    }

    public void setProfesorId(String profesorId) {
        ProfesorId = profesorId;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
