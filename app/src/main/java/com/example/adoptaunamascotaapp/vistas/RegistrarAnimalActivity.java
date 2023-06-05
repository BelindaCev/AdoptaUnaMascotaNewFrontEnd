package com.example.adoptaunamascotaapp.vistas;

import static com.example.adoptaunamascotaapp.R.id.radioGroupCategories;
import static com.example.adoptaunamascotaapp.R.id.radioGroupSubcategories;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adoptaunamascotaapp.R;

public class RegistrarAnimalActivity extends AppCompatActivity {

    public RadioGroup radioGroupCategories;
    public RadioGroup radioGroupSubcategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_animal);

        radioGroupCategories = findViewById(R.id.radioGroupCategories);
        radioGroupSubcategories = findViewById(R.id.radioGroupSubcategories);

        radioGroupCategories.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Mostrar u ocultar las subcategorías según la selección de categoría
                if (checkedId == R.id.radioButtonCat) {
                    radioGroupSubcategories.setVisibility(View.VISIBLE);
                    findViewById(R.id.radioButtonCatUnder6Months).setVisibility(View.VISIBLE);
                    findViewById(R.id.radioButtonCatOver6Months).setVisibility(View.VISIBLE);
                    findViewById(R.id.radioButtonDogSmall).setVisibility(View.GONE);
                    findViewById(R.id.radioButtonDogMedium).setVisibility(View.GONE);
                    findViewById(R.id.radioButtonDogLarge).setVisibility(View.GONE);
                } else if (checkedId == R.id.radioButtonDog) {
                    radioGroupSubcategories.setVisibility(View.VISIBLE);
                    findViewById(R.id.radioButtonCatUnder6Months).setVisibility(View.GONE);
                    findViewById(R.id.radioButtonCatOver6Months).setVisibility(View.GONE);
                    findViewById(R.id.radioButtonDogSmall).setVisibility(View.VISIBLE);
                    findViewById(R.id.radioButtonDogMedium).setVisibility(View.VISIBLE);
                    findViewById(R.id.radioButtonDogLarge).setVisibility(View.VISIBLE);
                } else {
                    radioGroupSubcategories.setVisibility(View.GONE);
                    findViewById(R.id.radioButtonCatUnder6Months).setVisibility(View.GONE);
                    findViewById(R.id.radioButtonCatOver6Months).setVisibility(View.GONE);
                    findViewById(R.id.radioButtonDogSmall).setVisibility(View.GONE);
                    findViewById(R.id.radioButtonDogMedium).setVisibility(View.GONE);
                    findViewById(R.id.radioButtonDogLarge).setVisibility(View.GONE);
                }
            }
        });
    }
}
