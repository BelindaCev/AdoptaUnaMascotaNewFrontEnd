package com.example.adoptaunamascotaapp.vistas;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adoptaunamascotaapp.R;
import com.example.adoptaunamascotaapp.modelos.Animal;
import com.example.adoptaunamascotaapp.tipos.SubcategoriaGato;

import java.util.ArrayList;

public class ListaAnimalesAdminActivity extends AppCompatActivity {

   ArrayList<Animal> listaAnimales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_animales_admin);

        listaAnimales = new ArrayList<>();

        ListView listViewAnimales = findViewById(R.id.lista_animales_admin);

        listaAnimales.add(new Animal(1, "Perro", SubcategoriasPerros.GRANDE.name(), "Max", "01/01/2019", "Macho", "Bulldog", "Max es un perro juguetón y amigable.", "05/05/2023", "icono_perro"));
        listaAnimales.add(new Animal(1, "Perro", SubcategoriasPerros.MEDIANO.name(), "Paquito", "02/07/2010", "Macho", "Beagle", "Paquito es un adorable.", "05/05/2023", "icono_perro"));
        listaAnimales.add(new Animal(2, "Gato", SubcategoriaGato.MAYOR_DE_6_MESES.name(), "Luna", "2/06/2019", "Hembra", "Común", "Luna es una gata tranquila y cariñosa.", "10/06/2023", "icono_gato"));
        listaAnimales.add(new Animal(2, "Gato", SubcategoriaGato.MENOR_DE_6_MESES.name(), "Bonyo", "15/03/2020", "Hembra", "Siamés", "Bonyo es una gata agresiva y le como la cara.", "10/06/2023", "icono_gato"));

        AnimalesAdminAdapter adapter = new AnimalesAdminAdapter(this, R.layout.item_animal_admin, listaAnimales);
        listViewAnimales.setAdapter(adapter);
    }
}




