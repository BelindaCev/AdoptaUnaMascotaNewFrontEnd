package com.example.adoptaunamascotaapp.modelos;

import com.example.adoptaunamascotaapp.tipos.TipoUsuario;
import com.google.gson.annotations.SerializedName;


import java.io.Serializable;

public class Usuario implements Serializable {
    @SerializedName("id")
    private Long id;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("apellidos")
    private String apellidos;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("tipoUsuario")
    private TipoUsuario tipoUsuario;

    public Usuario(long l, String nombre, String apellidos, String mail, String password, TipoUsuario user) {
        this.id=id;
        this.email = mail;
        this.password = password;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.tipoUsuario = TipoUsuario.USER;
    }

    public Usuario(String nombre, String apellidos, String mail) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = mail;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String mail) {
        this.email = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }
    public String getApellidos(){
        return apellidos;
    }
    public String setSurname(String surname){
        this.apellidos =surname;
        return surname;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
