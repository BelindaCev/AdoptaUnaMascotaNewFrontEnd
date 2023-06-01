package com.example.adoptaunamascotaapp.modelos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Animal implements Serializable {
    @SerializedName("id")
    long id;
    @SerializedName("categoria")
    String categoria;
    @SerializedName("subcategoria")
    String subcategoria; //tamaño/meses
    @SerializedName("nombre")
    String nombre;
    @SerializedName("fechaNacimiento")
    private String fechaNacimiento;
    @SerializedName("sexo")
    private String sexo;
    @SerializedName("raza")
    String raza;
    @SerializedName("descripcion")
    String descripcion;
    @SerializedName("fechaCreacion")
    String fechaCreacion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }



    public Animal() {
    }

    public Animal(long id, String categoria, String subcategoria, String nombre, String fechaNacimiento, String sexo, String raza, String descripcion, String fechaCreacion, String imageBase64) {
        this.id = id;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.raza = raza;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;

    }
}

