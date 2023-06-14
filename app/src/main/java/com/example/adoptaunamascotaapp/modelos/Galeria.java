package com.example.adoptaunamascotaapp.modelos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Galeria implements Serializable {
    @SerializedName("id")
    long id;
    @SerializedName("idAnimal")
    long idAnimal;
    @SerializedName("rutaFoto")
    String rutaFoto;
    @SerializedName("fechaCreacion")
    String fechaCreacion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(long idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Galeria(long id, long idAnimal) {
        this.id = id;
        this.idAnimal = idAnimal;
    }
}

