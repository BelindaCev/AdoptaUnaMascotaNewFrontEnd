package com.example.adoptaunamascotaapp.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adoptaunamascotaapp.R;
import com.example.adoptaunamascotaapp.modelos.Usuario;
import com.example.adoptaunamascotaapp.repository.UserRepository;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecoverPasswordActivity extends AppCompatActivity {

    EditText emailET;
    Button enviar;
    UserRepository userRepository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_pass);
        emailET = findViewById(R.id.mailEditText);
        enviar = findViewById(R.id.buttonRecordarPass);


        userRepository = new UserRepository();



        enviar.setOnClickListener(v -> {
            String email = emailET.getText().toString().trim();
            if (validateFields(email)&& correoCorrecto(email)) {
                Toast.makeText(RecoverPasswordActivity.this, "se ha enviado un correo con su nueva contraseña", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean validateFields(String email) {
        if (email.isEmpty()) {
            Toast.makeText(RecoverPasswordActivity.this, "El campo email no puede estar vacio", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean correoCorrecto(String email) {
     return userRepository.existeUsuarioEmail(email);
    }
    }
