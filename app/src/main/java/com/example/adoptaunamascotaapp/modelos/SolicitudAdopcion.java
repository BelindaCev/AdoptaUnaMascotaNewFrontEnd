package com.example.adoptaunamascotaapp.modelos;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class SolicitudAdopcion {
    @SerializedName("idAnimal")
    private Long idAnimal;
    @SerializedName("idUsuario")
    private Long idUsuario;
    @SerializedName("telefono")
    private String telefono;
    @SerializedName("detalleSolicitud")
    private String detalleSolicitud;
    @SerializedName("fechaNacimiento")
    private LocalDateTime edad;
    @SerializedName("domicilio")
    private String domicilio;

    /*
    public SolicitudAdopcion(Long idAnimal, String telefono, String detalleSolicitud, LocalDateTime edad, String domicilio) {
        this.idAnimal = idAnimal;
        this.telefono = telefono;
        this.detalleSolicitud = detalleSolicitud;
        this.edad = edad;
        this.domicilio = domicilio;
    }
     */

    public SolicitudAdopcion(Long idAnimal, Long idUsuario, String telefono, String detalleSolicitud, LocalDateTime edad, String domicilio) {
        this.idAnimal = idAnimal;
        this.idUsuario = idUsuario;
        this.telefono = telefono;
        this.detalleSolicitud = detalleSolicitud;
        this.edad = edad;
        this.domicilio = domicilio;
    }

    public Long getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(Long idAnimal) {
        this.idAnimal = idAnimal;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDetalleSolicitud() {
        return detalleSolicitud;
    }

    public void setDetalleSolicitud(String detalleSolicitud) {
        this.detalleSolicitud = detalleSolicitud;
    }

    public LocalDateTime getEdad() {
        return edad;
    }

    public void setEdad(LocalDateTime edad) {
        this.edad = edad;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }
}
