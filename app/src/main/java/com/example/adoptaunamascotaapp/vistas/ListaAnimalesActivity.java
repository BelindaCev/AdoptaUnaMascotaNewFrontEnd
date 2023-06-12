package com.example.adoptaunamascotaapp.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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

public class ListaAnimalesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    List<Animal> listaAnimales;
    private Spinner subcategorySpinner;
    private AnimalRepository animalRepository;
    private List<Animal> animalesFiltrados;
    private AnimalesAdapter adapter;
    private  String categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_animales);

        listaAnimales = new ArrayList<>();
        animalRepository = new AnimalRepository();
        animalesFiltrados = new ArrayList<>();

        ListView listViewAnimales = findViewById(R.id.lista_animales);
        subcategorySpinner = findViewById(R.id.subcategorySpinner);

        listViewAnimales.setOnItemClickListener(this);

        // Obtener la categoría seleccionada del Intent
        categoria = getIntent().getStringExtra("categoria");

        // Llamamos el metodo get de animales
        animalRepository.getAnimals(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                if (response.isSuccessful()) {
                    listaAnimales = response.body();

                    //Filtramos la lista según la subcategoría
                    animalesFiltrados = filtrarAnimalPorCategoria(listaAnimales, categoria);

                    //Actualizamos la interfaz con la nueva lista de animales
                    adapter = new AnimalesAdapter(ListaAnimalesActivity.this, animalesFiltrados);
                    listViewAnimales.setAdapter(adapter);

                    initSubcategoriaSpinnerAdapter(categoria);

                    subcategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String subcategoria =  subcategorySpinner.getSelectedItem().toString();
                            List<Animal> animalesFiltradosPorSubcategoria = filtrarAnimalPorSubcategoria(animalesFiltrados, subcategoria);
                            adapter.actualizarLista(animalesFiltradosPorSubcategoria);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    //Ponemos el listener para cuando se clicke en el spinner

                }
            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                //Mensaje de error
                Toast.makeText(ListaAnimalesActivity.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSubcategoriaSpinnerAdapter(String categoria) {
        ArrayAdapter<CharSequence> subcategoriaAdapter;
        if (categoria.equals("Perro")) {
            subcategoriaAdapter = ArrayAdapter.createFromResource(ListaAnimalesActivity.this, R.array.subcategorias_perro, android.R.layout.simple_spinner_item);
        } else if (categoria.equals("Gato")) {
            subcategoriaAdapter = ArrayAdapter.createFromResource(ListaAnimalesActivity.this, R.array.subcategorias_gato, android.R.layout.simple_spinner_item);
        } else {
            subcategoriaAdapter = new ArrayAdapter<>(ListaAnimalesActivity.this, android.R.layout.simple_spinner_item);
        }
        subcategoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subcategorySpinner.setAdapter(subcategoriaAdapter);
    }

    private  List<Animal> filtrarAnimalPorCategoria(List<Animal> animales, String categoria) {
        List<Animal> animalesFiltrados = new ArrayList<>();

        for (Animal animal : animales){
            if (animal.getCategoria().equals(categoria)) {
                animalesFiltrados.add(animal);
            }
        }
        return  animalesFiltrados;
    }

    private  List<Animal> filtrarAnimalPorSubcategoria(List<Animal> animales, String subcategoria) {
        List<Animal> animalesFiltrados = new ArrayList<>();

        for (Animal animal : animales) {
            if (categoria.equals("Perro")) {
                if (animal.getSubcategoria().equals(subcategoria)) {
                    animalesFiltrados.add(animal);
                }
            } else if (categoria.equals("Gato")) {
                if (animal.getSubcategoria().equals(subcategoria)) {
                    animalesFiltrados.add(animal);
                }
            }
        }
        return animalesFiltrados;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(ListaAnimalesActivity.this, SolicitudAdopcionActivity.class);
        startActivity(intent);
    }
}