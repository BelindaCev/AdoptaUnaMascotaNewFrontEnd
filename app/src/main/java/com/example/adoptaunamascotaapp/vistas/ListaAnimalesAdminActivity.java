package com.example.adoptaunamascotaapp.vistas;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adoptaunamascotaapp.R;
import com.example.adoptaunamascotaapp.modelos.Animal;
import com.example.adoptaunamascotaapp.repository.AnimalRepository;
import com.example.adoptaunamascotaapp.tipos.SubcategoriaGato;
import com.example.adoptaunamascotaapp.tipos.SubcategoriasPerro;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaAnimalesAdminActivity extends AppCompatActivity {

    AnimalesAdminAdapter adapter;
   ArrayList<Animal> listaAnimales;
    private AnimalRepository animalRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_animales_admin);

        listaAnimales = new ArrayList<>();
        animalRepository = new AnimalRepository();

        ListView listViewAnimales = findViewById(R.id.lista_animales_admin);

        // Llama al método getAnimals() del repositorio para obtener la lista de animales
        animalRepository.getAnimals(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                if(response.isSuccessful()) {
                    listaAnimales = (ArrayList<Animal>) response.body();

                    //Actualizamos la interfaz
                    adapter = new AnimalesAdminAdapter(ListaAnimalesAdminActivity.this, R.layout.item_animal_admin, listaAnimales);
                    listViewAnimales.setAdapter(adapter);
                } else {
                    //Mensaje de error
                    Toast.makeText(ListaAnimalesAdminActivity.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                //Mensaje de error
                Toast.makeText(ListaAnimalesAdminActivity.this, "Se ha producido un error", Toast.LENGTH_SHORT).show();
            }
        });

        listViewAnimales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Animal animal = listaAnimales.get(position);

                //llamamos al metodo delete del repositorio
                animalRepository.deleteAnimal(animal.getId(), new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()) {
                            listaAnimales.remove(position);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(ListaAnimalesAdminActivity.this, "Animal eliminado correctamente", Toast.LENGTH_SHORT).show();
                        } else  {
                            Toast.makeText(ListaAnimalesAdminActivity.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ListaAnimalesAdminActivity.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}




