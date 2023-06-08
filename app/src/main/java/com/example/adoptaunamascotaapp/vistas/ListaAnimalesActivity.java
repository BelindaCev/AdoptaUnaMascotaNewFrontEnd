package com.example.adoptaunamascotaapp.vistas;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adoptaunamascotaapp.R;
import com.example.adoptaunamascotaapp.modelos.Animal;
import com.example.adoptaunamascotaapp.tipos.SubcategoriaGato;
import com.example.adoptaunamascotaapp.tipos.SubcategoriasPerros;

import java.util.ArrayList;
import java.util.List;

public class ListaAnimalesActivity extends AppCompatActivity {

    List<Animal> listaAnimales;
    private Spinner subcategorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_animales);

        listaAnimales = new ArrayList<>();

        ListView listViewAnimales = findViewById(R.id.lista_animales);
        subcategorySpinner = findViewById(R.id.subcategorySpinner);

        // Obtener la categoría seleccionada del Intent
        String categoria = getIntent().getStringExtra("categoria");

        // Crear una lista de objetos Animal
        if (categoria.equals("Perro")) {
            listaAnimales.add(new Animal(1, "Perro", SubcategoriasPerros.GRANDE.name(), "Max", "01/01/2019", "Macho", "Bulldog", "Max es un perro juguetón y amigable.", "05/05/2023", "icono_perro"));
            listaAnimales.add(new Animal(1, "Perro", SubcategoriasPerros.MEDIANO.name(), "Paquito", "02/07/2010", "Macho", "Beagle", "Paquito es un adorable.", "05/05/2023", "icono_perro"));
        } else if (categoria.equals("Gato")) {
            listaAnimales.add(new Animal(2, "Gato", SubcategoriaGato.MAYOR_DE_6_MESES.name(), "Luna", "2/06/2019", "Hembra", "Común", "Luna es una gata tranquila y cariñosa.", "10/06/2023", "icono_gato"));
            listaAnimales.add(new Animal(2, "Gato", SubcategoriaGato.MENOR_DE_6_MESES.name(), "Bonyo", "15/03/2020", "Hembra", "Siamés", "Bonyo es una gata agresiva y le como la cara.", "10/06/2023", "icono_gato"));
        }

        AnimalesAdapter adapter = new AnimalesAdapter(this, listaAnimales);
        listViewAnimales.setAdapter(adapter);

        // Configurar el adaptador del Spinner según la categoría seleccionada
        ArrayAdapter<CharSequence> subcategoryAdapter;
        if (categoria.equals("Perro")) {
            subcategoryAdapter = ArrayAdapter.createFromResource(this, R.array.subcategorias_perro, android.R.layout.simple_spinner_item);
        } else if (categoria.equals("Gato")) {
            subcategoryAdapter = ArrayAdapter.createFromResource(this, R.array.subcategorias_gato, android.R.layout.simple_spinner_item);
        } else {
            // Manejar caso de categoría inválida
            subcategoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        }

        subcategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subcategorySpinner.setAdapter(subcategoryAdapter);
    }
}




