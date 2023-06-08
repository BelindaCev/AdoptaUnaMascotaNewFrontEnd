package com.example.adoptaunamascotaapp.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adoptaunamascotaapp.R;

public class HomeActivity extends AppCompatActivity {

    private ImageButton perroImageButton;
    private ImageButton gatoImageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        perroImageButton = findViewById(R.id.perro);
        gatoImageButton = findViewById(R.id.gato);

        perroImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirListaAnimales("Perro");
            }
        });

        gatoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirListaAnimales("Gato");
            }
        });
    }

    private void abrirListaAnimales(String categoria) {
        Intent intent = new Intent(HomeActivity.this, ListaAnimalesActivity.class);
        intent.putExtra("categoria", categoria);
        startActivity(intent);
    }
}

