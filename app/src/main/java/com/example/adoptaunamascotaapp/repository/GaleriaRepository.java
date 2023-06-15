package com.example.adoptaunamascotaapp.repository;

import com.example.adoptaunamascotaapp.modelos.Galeria;
import com.example.adoptaunamascotaapp.service.ApiService;


import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class GaleriaRepository extends AbstractRepository{
    private final ApiService apiService;

    public GaleriaRepository() {

        apiService = retrofit.create(ApiService.class);
    }
    public void getGaleria(Callback<List<Galeria>> callback, Long idAnimal) {
        Call<List<Galeria>> call = apiService.getGaleria(idAnimal);
        call.enqueue(callback);
    }

    public void createGaleria(Long idAnimal, File foto, Callback <Galeria> callback) {
        // Crea el cuerpo de la solicitud multipart
        RequestBody requestBody = RequestBody.create(foto, MediaType.parse("multipart/form-data"));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", foto.getName(), requestBody);

        Call<Galeria> call = apiService.createGaleria(idAnimal, filePart);

        call.enqueue(callback);
    }
}
