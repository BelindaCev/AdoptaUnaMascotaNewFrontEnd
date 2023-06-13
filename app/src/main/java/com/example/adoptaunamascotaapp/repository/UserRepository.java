package com.example.adoptaunamascotaapp.repository;

import android.util.Log;
import android.widget.Toast;


import com.example.adoptaunamascotaapp.modelos.Usuario;
import com.example.adoptaunamascotaapp.service.ApiService;
import com.example.adoptaunamascotaapp.tipos.PasswordHttp;
import com.example.adoptaunamascotaapp.vistas.RecuperarPasswordActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserRepository extends AbstractRepository{
    private final ApiService apiService;

    public UserRepository() {

        apiService = retrofit.create(ApiService.class);
    }

    public void getUserByEmailAndPassword(String email, String rawPassword, Callback<Usuario> callback) {
        PasswordHttp passwordHttp = new PasswordHttp();
        passwordHttp.setEmail(email);
        passwordHttp.setPassword(rawPassword);
        Call<Usuario> call = apiService.loginUser(passwordHttp);
        Log.d("UserRepository", "getUserByEmailAndPassword: URL=" + call.request().url());
        call.enqueue(callback);
    }


    public void registerUser(Usuario usuario, Callback<Usuario> callback) {
        Call<Usuario> call = apiService.createUser(usuario);
        call.enqueue(callback);
    }
    public void updateUserPassword(String email, String newPassword, Callback<Usuario> callback) {
        Call<Usuario> call = apiService.updateUserPassword(email, newPassword);
        call.enqueue(callback);
    }
    public void getUsers(Callback<List<Usuario>> callback) {
        Call<List<Usuario>> call = apiService.getUsers();
        Log.d("UserRepository", "getUsers: URL=" + call.request().url()); // Agrega este registro
        call.enqueue(callback);
    }
    public void createUser(Usuario usuario, Callback<Usuario> callback) {
        Call<Usuario> call = apiService.createUser(usuario);
        call.enqueue(callback);
    }
    public void updateUser(Usuario usuario, Callback<Usuario> callback) {
        Call<Usuario> call = apiService.updateUser(usuario);
        call.enqueue(callback);
    }

    public void deleteUser(long id, Callback<Void> callback) {
        Call<Void> call = apiService.deleteUser(id);
        call.enqueue(callback);
    }
    public void existeUsuarioEmail(String email, Callback<Boolean> callback) {
        Call<Boolean> call = apiService.existeUsuarioEmail(email);
        call.enqueue(callback);
    }
}
