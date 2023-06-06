package com.example.adoptaunamascotaapp.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adoptaunamascotaapp.R;

public class HomeAdminActivity extends AppCompatActivity {

    ImageButton usuarioIB;
    ImageButton animalIB;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        usuarioIB = findViewById(R.id.usuario);
        animalIB = findViewById(R.id.animales);

        usuarioIB.setOnClickListener(v -> {
            Intent intent = new Intent(HomeAdminActivity.this, RecuperarPasswordActivity.class);
            startActivity(intent);
        });
        animalIB.setOnClickListener(v -> {
            Intent intent = new Intent(HomeAdminActivity.this, RegistrarAnimalActivity.class);
            startActivity(intent);
        });
    }
}
