package com.example.adoptaunamascotaapp.repository;

import com.example.adoptaunamascotaapp.modelos.Animal;
import com.example.adoptaunamascotaapp.service.ApiService;
import com.google.gson.Gson;


import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AnimalRepository extends AbstractRepository{
    private final ApiService apiService;

    public AnimalRepository() {

        apiService = retrofit.create(ApiService.class);
    }

    public void getAnimals(Callback<List<Animal>> callback) {
        Call<List<Animal>> call = apiService.getAnimals();
        call.enqueue(callback);
    }

    public void createAnimal(Animal animal, Callback<Animal> callback) {
        // Convertir el objeto Animal a JSON
        Gson gson = new Gson();
        String animalJson = gson.toJson(animal);
        RequestBody animalRequestBody = RequestBody.create(MediaType.parse("application/json"), animalJson);

        Call<Animal> call = apiService.createAnimal(animalRequestBody);
        call.enqueue(callback);
    }




    public void updateAnimal(Animal animal, Callback<Animal> callback) {
        Call<Animal> call = apiService.updateAnimal(animal);
        call.enqueue(callback);
    }

    public void deleteAnimal(long id, Callback<Void> callback) {
        Call<Void> call = apiService.deleteAnimal(id);
        call.enqueue(callback);
    }
}
