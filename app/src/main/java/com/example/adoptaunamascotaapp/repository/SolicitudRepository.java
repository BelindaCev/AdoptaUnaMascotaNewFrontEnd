package com.example.adoptaunamascotaapp.repository;

import com.example.adoptaunamascotaapp.modelos.SolicitudAdopcion;
import com.example.adoptaunamascotaapp.service.ApiService;


import retrofit2.Callback;

public class SolicitudRepository extends AbstractRepository{

    private final ApiService apiService;


    public SolicitudRepository() {

        apiService = retrofit.create(ApiService.class);
    }
    public void registerSolicitud(SolicitudAdopcion solicitud, Callback<SolicitudAdopcion> callback){

    }

}
