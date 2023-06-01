package com.example.adoptaunamascotaapp.service;



import com.example.adoptaunamascotaapp.modelos.Animal;
import com.example.adoptaunamascotaapp.modelos.Galeria;
import com.example.adoptaunamascotaapp.modelos.SolicitudAdopcion;
import com.example.adoptaunamascotaapp.modelos.Usuario;
import com.example.adoptaunamascotaapp.tipos.PasswordHttp;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    //Usuarios

    @POST("api/usuario/auth")
    Call<Usuario> loginUser(@Body PasswordHttp passwordHttp);


    @POST("/api/usuario")
    Call<Usuario> createUser(@Body Usuario usuario);

    @POST("/api/usuario/password")
    Call<Usuario> updateUserPassword(@Query("email") String email, @Query("newPassword") String newPassword);

    //Para CRUD
    @GET("/api/usuario")
    Call<List<Usuario>> getUsers();

    @PUT("/api/usuario")
    Call<Usuario> updateUser(@Body Usuario usuario);

    @GET("/api/usuario/email")
    boolean existeUsuarioEmail(@Query("email") String email);

    @DELETE("api/usuario/{id}")
    Call<Void> deleteUser(@Path("id") long id);

    //Animales
    @GET("api/animals/")
    Call<List<Animal>> getAnimals();

    @POST("api/animals/")
    Call<Animal> createAnimal(@Body RequestBody animal);


    @PUT("api/animals/{id}")
    Call<Animal> updateAnimal( @Body Animal animal);

    @DELETE("api/animals/{id}")
    Call<Void> deleteAnimal(@Path("id") long id);



    @GET ("/api/galeria")
    Call <List<Galeria>> getGaleria (@Query("idAnimal") Long idAnimal);
        @Multipart

    @PUT("/api/galeria")
    Call <Galeria> createGaleria (@Query("idAnimal") Long idAnimal, @Body MultipartBody.Part foto);
}
