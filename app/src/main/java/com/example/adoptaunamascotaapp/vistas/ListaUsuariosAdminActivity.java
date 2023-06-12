package com.example.adoptaunamascotaapp.vistas;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adoptaunamascotaapp.R;
import com.example.adoptaunamascotaapp.modelos.Usuario;
import com.example.adoptaunamascotaapp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaUsuariosAdminActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ArrayList<Usuario> listaUsuarios;
    UserRepository userRepository;

    UsuariosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios_admin);

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

        // Llamar al método deleteUser del repositorio
        userRepository.deleteUser(usuarioId, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Eliminar el usuario de la lista
                    listaUsuarios.remove(position);
                    adapter.notifyDataSetChanged();
                } else {
                    // Manejar respuesta no exitosa
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Manejar error de red u otro tipo de error
            }
        });
    }
}
