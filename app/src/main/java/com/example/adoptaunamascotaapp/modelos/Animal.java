package com.example.adoptaunamascotaapp.modelos;

import com.example.adoptaunamascotaapp.tipos.SexoEnum;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDate;

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
    private LocalDate fechaNacimiento;
    @SerializedName("sexo")
    private SexoEnum sexo;
    @SerializedName("raza")
    String raza;
    @SerializedName("descripcion")
    String descripcion;
    @SerializedName("fechaCreacion")
    LocalDate fechaCreacion;

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public SexoEnum getSexo() {
        return sexo;
    }

    //Prueba
    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

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





    public Animal() {
    }

    public Animal(long id, String categoria, String subcategoria, String nombre, LocalDate fechaNacimiento, SexoEnum sexo, String raza, String descripcion) {
        this.id = id;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.raza = raza;
        this.descripcion = descripcion;

    }
}

