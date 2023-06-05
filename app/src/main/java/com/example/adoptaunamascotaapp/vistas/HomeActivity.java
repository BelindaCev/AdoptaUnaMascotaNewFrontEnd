package com.example.adoptaunamascotaapp.vistas;



import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adoptaunamascotaapp.R;

public class HomeActivity extends AppCompatActivity {

    ImageButton perroIB;
    ImageButton gatoIB;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        perroIB = findViewById(R.id.perro);
        gatoIB = findViewById(R.id.gato);

        perroIB.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RecuperarPasswordActivity.class);
            startActivity(intent);
        });
    }


}
