package com.example.adoptaunamascotaapp.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adoptaunamascotaapp.R;
import com.example.adoptaunamascotaapp.modelos.Usuario;
import com.example.adoptaunamascotaapp.repository.UserRepository;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistraseActivity extends AppCompatActivity {

    private EditText nombreET, apellidosET, emailET, passwordET, passwords2ET;
    private Button registrarseBTN;

    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        apellidosET = findViewById(R.id.apellidosEditText);
        nombreET = findViewById(R.id.nombreEditText);
        emailET = findViewById(R.id.emaillEditText);
        passwordET = findViewById(R.id.passwordEditText);
        passwords2ET = findViewById(R.id.repeatPasswordEditText);
        registrarseBTN = findViewById(R.id.botonRegistrate);


        userRepository = new UserRepository();

        registrarseBTN.setOnClickListener(v -> {
            if (validateFields()) {
                registerUser();
            }
        });
    }
    private boolean validateFields() {
        if (nombreET.getText().toString().isEmpty() || apellidosET.getText().toString().isEmpty()
                || emailET.getText().toString().isEmpty() || passwordET.getText().toString().isEmpty()
                || passwords2ET.getText().toString().isEmpty()) {
            Toast.makeText(RegistraseActivity.this, "Debe rellenar todos los campos",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (nombreET.getText().toString().isEmpty()) {
            Toast.makeText(RegistraseActivity.this, "Rellene el campo de nombre",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (apellidosET.getText().toString().isEmpty()) {
            Toast.makeText(RegistraseActivity.this, "Rellene el campo de apellidos",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (emailET.getText().toString().isEmpty()) {
            Toast.makeText(RegistraseActivity.this, "Rellene el campo de email",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordET.getText().toString().isEmpty()) {
            Toast.makeText(RegistraseActivity.this, "Rellene el campo de contraseña",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (!passwordET.getText().toString().equals(passwords2ET.getText().toString())) {
            Toast.makeText(RegistraseActivity.this, "Las contraseñas no coinciden",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void registerUser() {
        String password = passwordET.getText().toString();
        Usuario usuario = new Usuario(
                nombreET.getText().toString(),
                apellidosET.getText().toString(),
                emailET.getText().toString(),
                password
        );
        userRepository.registerUser(usuario, new Callback<Usuario>(){
            @Override
            public void onResponse(@NonNull Call<Usuario> call, @NonNull Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Usuario registeredUsuario = response.body();
                    runOnUiThread(() -> {
                        Toast.makeText(RegistraseActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegistraseActivity.this, LoginActivity.class);
                        assert registeredUsuario != null;
                        intent.putExtra("email", registeredUsuario.getEmail());
                        intent.putExtra("password", password);
                        startActivity(intent);
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(RegistraseActivity.this, "Error en el registro", Toast.LENGTH_SHORT).show();
                    });
                }
            }
            @Override
            public void onFailure(@NonNull Call<Usuario> call, @NonNull Throwable t) {
            }
        });
    }
}