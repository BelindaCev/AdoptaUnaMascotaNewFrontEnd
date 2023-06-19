package com.example.adoptaunamascotaapp.vistas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adoptaunamascotaapp.R;
import com.example.adoptaunamascotaapp.modelos.Usuario;
import com.example.adoptaunamascotaapp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaUsuariosAdminActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    ArrayList<Usuario> listaUsuarios;
    UserRepository userRepository;

    ImageButton nuevoUsuarioButton;

    ImageView irHome;

    UsuariosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios_admin);

        nuevoUsuarioButton = findViewById(R.id.botonCrearUsuario);

        irHome = findViewById(R.id.irHome);

        irHome.setOnClickListener(this);

        nuevoUsuarioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirDarAltaUsuario();
            }
        });

        listaUsuarios = new ArrayList<>();
        ListView listViewUsuarios = findViewById(R.id.lista_usuarios);

        adapter = new UsuariosAdapter(this, R.layout.item_usuario, listaUsuarios);
        listViewUsuarios.setAdapter(adapter);

        // Crear una instancia del UserRepository
        userRepository = new UserRepository();

        // Llamar al método getUsers del repositorio
        userRepository.getUsers(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    List<Usuario> usuarios = response.body();
                    // Actualizar la lista de usuarios y notificar al adaptador de cambios
                    listaUsuarios.clear();
                    listaUsuarios.addAll(usuarios);
                    adapter.notifyDataSetChanged();
                } else {
                    // Manejar respuesta no exitosa
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                // Manejar error de red u otro tipo de error
            }
        });

        //Llamada al método que se produce cuando se pulsa un elemento de la lista
        listViewUsuarios.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Usuario usuario = listaUsuarios.get(position);
        long usuarioId = usuario.getId();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar eliminación");
        builder.setMessage("¿Estás seguro de que deseas eliminar este usuario?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Llamar al método deleteUser del repositorio
                userRepository.deleteUser(usuarioId, new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Eliminar el usuario de la lista
                            listaUsuarios.remove(position);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(ListaUsuariosAdminActivity.this, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ListaUsuariosAdminActivity.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ListaUsuariosAdminActivity.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton("No", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void abrirDarAltaUsuario() {
        Intent intent = new Intent(ListaUsuariosAdminActivity.this, RegistrarNuevoUsuarioActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ListaUsuariosAdminActivity.this, HomeAdminActivity.class);
        startActivity(intent);
    }
}
