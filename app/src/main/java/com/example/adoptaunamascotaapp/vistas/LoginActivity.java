package com.example.adoptaunamascotaapp.vistas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adoptaunamascotaapp.R;
import com.example.adoptaunamascotaapp.modelos.Usuario;
import com.example.adoptaunamascotaapp.repository.UserRepository;
import com.example.adoptaunamascotaapp.tipos.TipoUsuario;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    TextView registerTextView;
    TextView forgetPasswordTextView;
    Button loginButton;
    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userRepository = new UserRepository();
        emailEditText = findViewById(R.id.nombreUsuarioEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerTextView = findViewById(R.id.aquiRegistrate);
        forgetPasswordTextView = findViewById(R.id.aquiRecordarPass);
        loginButton = findViewById(R.id.loginButton);

        registerTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        forgetPasswordTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RecoverPasswordActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String rawPassword = passwordEditText.getText().toString();
            validateFills(email, rawPassword);

            if (!email.isEmpty() && !rawPassword.isEmpty()) {
                userRepository.getUserByEmailAndPassword(email, rawPassword, new Callback<Usuario>() {
                    @Override
                    public void onResponse(@NonNull Call<Usuario> call, @NonNull Response<Usuario> response) {
                        if (response.isSuccessful()) {
                            Usuario usuario = response.body();
                            if (usuario != null && usuario.getId() != null) {
                                Log.d("LoginActivity", "User autenticado con éxito, id: " + usuario.getId());
                                saveUserId(usuario.getId());
                                if(usuario.getTipoUsuario() == TipoUsuario.ADMIN){
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.putExtra("user", usuario);
                                    startActivity(intent);
                                }else{
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.putExtra("user", usuario);
                                    startActivity(intent);
                                }

                            } else {
                                Log.d("LoginActivity", "Usuario o ID nulos.");
                            }
                        } else {
                            Log.d("LoginActivity", "Respuesta no exitosa: " + response.code());
                            Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Usuario> call, @NonNull Throwable t) {
                        Toast.makeText(LoginActivity.this, "Error de red, inténtalo de nuevo", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void validateFills(String email, String rawPassword) {
        if (email.isEmpty() && rawPassword.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Rellene el campo de email", Toast.LENGTH_SHORT).show();
        } else if (rawPassword.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Rellene el campo de contraseña", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserId(Long userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("userId", userId);
        editor.commit();

        long savedUserId = sharedPreferences.getLong("userId", 0);
        if (savedUserId == userId) {
            Log.d("LoginActivity", "UserId guardado correctamente: " + userId);
        } else {
            Log.e("LoginActivity", "Error al guardar UserId. Se esperaba " + userId + " pero se guardó " + savedUserId);
        }
    }
}