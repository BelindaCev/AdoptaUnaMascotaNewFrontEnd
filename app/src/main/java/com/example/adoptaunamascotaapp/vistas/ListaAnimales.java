package com.example.adoptaunamascotaapp.vistas;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adoptaunamascotaapp.R;
import com.example.adoptaunamascotaapp.modelos.Animal;

import java.util.ArrayList;
import java.util.List;

public class ListaAnimales extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_animales);

        ListView listView = findViewById(R.id.listaAnimales);

        // Crear una lista de objetos Animal (reemplaza esto con tu propia lista)
        List<Animal> animalList = new ArrayList<>();
        animalList.add(new Animal(1, "Perro", "Pequeño", "Max", "01/01/2019", "Macho", "Bulldog", "Max es un perro juguetón y amigable.", "05/05/2023", "icono_perro"));
        animalList.add(new Animal(2, "Gato", "Mediano", "Luna", "15/03/2020", "Hembra", "Siamés", "Luna es una gata tranquila y cariñosa.", "10/06/2023", "icono_gato"));

        ListaAdapter adapter = new ListaAdapter(this, R.layout.activity_lista_item, animalList);
        listView.setAdapter(adapter);
    }
}

