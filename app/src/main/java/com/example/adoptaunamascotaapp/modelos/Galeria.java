package com.example.adoptaunamascotaapp.modelos;

import com.google.gson.annotations.SerializedName;

public class Galeria {
    @SerializedName("id")
    long id;
    @SerializedName("idAnimal")
    long idAnimal;
    @SerializedName("rutaFoto")
    String rutaFoto;
    @SerializedName("fechaCreacion")
    String fechaCreacion;
}

