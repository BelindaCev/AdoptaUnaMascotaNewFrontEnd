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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaAnimalesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    List<Animal> listaAnimales;

    private Spinner filtro;
    private AnimalRepository animalRepository;
    private List<Animal> animalesFiltrados;
    private AnimalesAdapter adapter;
    private String categoria;

    // Arrays de subcategorías de perros y gatos
    private String[] subcategoriasPerro;
    private String[] subcategoriasGato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_animales);

        listaAnimales = new ArrayList<>();
        animalRepository = new AnimalRepository();
        animalesFiltrados = new ArrayList<>();

        ListView listViewAnimales = findViewById(R.id.lista_animales);
        listViewAnimales.setOnItemClickListener(this);

        filtro = findViewById(R.id.subcategorySpinner);

        // Obtener la categoría seleccionada del Intent
        categoria = getIntent().getStringExtra("categoria");

        // Obtener los arrays de subcategorías según la categoría
        if (categoria.equals("Perro")) {
            subcategoriasPerro = getResources().getStringArray(R.array.subcategorias_perro);
            cargarSpinner(subcategoriasPerro);
        } else if (categoria.equals("Gato")) {
            subcategoriasGato = getResources().getStringArray(R.array.subcategorias_gato);
            cargarSpinner(subcategoriasGato);
        }

        // Llamamos al método get de animales
        animalRepository.getAnimals(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                if (response.isSuccessful()) {
                    listaAnimales = response.body();

                    // Filtramos la lista según la categoría seleccionada
                    animalesFiltrados = filtrarAnimalPorCategoria(listaAnimales, categoria);

                    // Actualizamos la interfaz con la nueva lista de animales
                    adapter = new AnimalesAdapter(ListaAnimalesActivity.this, animalesFiltrados);
                    listViewAnimales.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                // Mensaje de error
                Toast.makeText(ListaAnimalesActivity.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
            }
        });

        // Manejar los eventos de selección del Spinner
        filtro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String subcategoriaSeleccionada = parent.getItemAtPosition(position).toString();
                if (subcategoriaSeleccionada.equals("Filtrar")) {
                    animalesFiltrados = filtrarAnimalPorCategoria(listaAnimales, categoria);
                } else {
                    animalesFiltrados = filtrarAnimalPorCategoriaSubcategoria(listaAnimales, categoria, subcategoriaSeleccionada);
                }
                adapter.actualizarLista(animalesFiltrados);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No es necesario hacer nada
            }
        });
    }

    //Método para rellenar el spinner con un Array de Strings u otro en función de la categoría recibida
    private void cargarSpinner(String[] subcategorias) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subcategorias);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filtro.setAdapter(spinnerAdapter);
        filtro.setSelection(0); // Establecer "Filtrar" como opción seleccionada por defecto
    }

    private List<Animal> filtrarAnimalPorCategoria(List<Animal> animales, String categoria) {
        List<Animal> animalesFiltrados = new ArrayList<>();

        for (Animal animal : animales) {
            if (animal.getCategoria().equals(categoria)) {
                animalesFiltrados.add(animal);
            }
        }

        return animalesFiltrados;
    }

    private List<Animal> filtrarAnimalPorCategoriaSubcategoria(List<Animal> animales, String categoria, String subcategoria) {
        List<Animal> animalesFiltrados = new ArrayList<>();

        for (Animal animal : animales) {
            if (animal.getCategoria().equals(categoria) && animal.getSubcategoria().equals(subcategoria)) {
                animalesFiltrados.add(animal);
            }
        }

        return animalesFiltrados;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Animal animalSeleccionado = animalesFiltrados.get(position);
        long idAnimal = animalSeleccionado.getId();
        String nombreAnimal = animalSeleccionado.getNombre();
        String descripcionAnimal = animalSeleccionado.getDescripcion();

        Intent intent = new Intent(ListaAnimalesActivity.this, SolicitudAdopcionActivity.class);
        intent.putExtra("idAnimal", idAnimal);
        intent.putExtra("nombreAnimal", nombreAnimal);
        intent.putExtra("descripcionAnimal", descripcionAnimal);
        startActivity(intent);
        /*
        Intent intent = new Intent(ListaAnimalesActivity.this, SolicitudAdopcionActivity.class);
        startActivity(intent);
         */
    }

}