package com.example.adoptaunamascotaapp.vistas;

import static com.example.adoptaunamascotaapp.R.id.botonRegistrarUsuario;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adoptaunamascotaapp.R;
import com.example.adoptaunamascotaapp.modelos.Usuario;
import com.example.adoptaunamascotaapp.repository.UserRepository;
import com.example.adoptaunamascotaapp.tipos.TipoUsuario;



import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarNuevoUsuarioActivity extends AppCompatActivity {

    private EditText nombreET, apellidosET, emailET, passwordET, passwords2ET;
    private Button registrarBTN;
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
        registrarBTN = findViewById(R.id.botonRegistrarUsuario);

        userRepository = new UserRepository();

        registrarBTN.setOnClickListener(v -> {
            if (validateFields()) {
                registerUser();
            }
        });
    }

    private boolean validateFields() {
        // Validar los campos de texto
        if (nombreET.getText().toString().isEmpty() || apellidosET.getText().toString().isEmpty()
                || emailET.getText().toString().isEmpty() || passwordET.getText().toString().isEmpty()
                || passwords2ET.getText().toString().isEmpty()) {
            Toast.makeText(RegistrarNuevoUsuarioActivity.this, "Debe rellenar todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!passwordET.getText().toString().equals(passwords2ET.getText().toString())) {
            Toast.makeText(RegistrarNuevoUsuarioActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void registerUser() {
        String password = passwordET.getText().toString();
        Usuario usuario = new Usuario(
                1L, // Cambiar el valor del ID según corresponda
                nombreET.getText().toString(),
                apellidosET.getText().toString(),
                emailET.getText().toString(),
                password,
                TipoUsuario.USER // Cambiar el tipo de usuario según corresponda
        );

        userRepository.registerUser(usuario, new Callback<Usuario>() {
            @Override
            public void onResponse(retrofit2.Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Usuario registeredUsuario = response.body();
                    runOnUiThread(() -> {
                        Toast.makeText(RegistrarNuevoUsuarioActivity.this, "Usuario dado de alta exitosamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegistrarNuevoUsuarioActivity.this, LoginActivity.class);
                        assert registeredUsuario != null;
                        intent.putExtra("email", registeredUsuario.getEmail());
                        intent.putExtra("password", password);
                        startActivity(intent);
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(RegistrarNuevoUsuarioActivity.this, "Error en el alta del usuario", Toast.LENGTH_SHORT).show();
                    });
                }
            }
            @Override
            public void onFailure(retrofit2.Call<Usuario> call, Throwable t) {
            }
        });
    }
}