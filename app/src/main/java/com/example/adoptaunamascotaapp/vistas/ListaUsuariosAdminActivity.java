package com.example.adoptaunamascotaapp.vistas;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adoptaunamascotaapp.R;
import com.example.adoptaunamascotaapp.modelos.Usuario;
import com.example.adoptaunamascotaapp.tipos.TipoUsuario;

import java.util.ArrayList;

public class ListaUsuariosAdminActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ArrayList<Usuario> listaUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios_admin);

        listaUsuarios = new ArrayList<>();
        ListView listViewUsuarios = findViewById(R.id.lista_usuarios);

        listaUsuarios.add(new Usuario(1L, "Belinda", "Cuesta", "belinda@example.com", "password", TipoUsuario.ADMIN));
        listaUsuarios.add(new Usuario(2L, "Paula", "Muerte", "paula@example.com", "password", TipoUsuario.ADMIN));
        listaUsuarios.add(new Usuario(2L, "Raquel", "Alvarez", "raquel@example.com", "password", TipoUsuario.ADMIN));
        listaUsuarios.add(new Usuario(1L, "Belinda", "Cuesta", "belinda@example.com", "password", TipoUsuario.ADMIN));
        listaUsuarios.add(new Usuario(2L, "Paula", "Muerte", "paula@example.com", "password", TipoUsuario.ADMIN));
        listaUsuarios.add(new Usuario(2L, "Raquel", "Alvarez", "raquel@example.com", "password", TipoUsuario.ADMIN));
        listaUsuarios.add(new Usuario(1L, "Belinda", "Cuesta", "belinda@example.com", "password", TipoUsuario.ADMIN));
        listaUsuarios.add(new Usuario(2L, "Paula", "Muerte", "paula@example.com", "password", TipoUsuario.ADMIN));
        listaUsuarios.add(new Usuario(2L, "Raquel", "Alvarez", "raquel@example.com", "password", TipoUsuario.ADMIN));
        listaUsuarios.add(new Usuario(1L, "Belinda", "Cuesta", "belinda@example.com", "password", TipoUsuario.ADMIN));
        listaUsuarios.add(new Usuario(2L, "Paula", "Muerte", "paula@example.com", "password", TipoUsuario.ADMIN));
        listaUsuarios.add(new Usuario(2L, "Raquel", "Alvarez", "raquel@example.com", "password", TipoUsuario.ADMIN));
        listaUsuarios.add(new Usuario(1L, "Belinda", "Cuesta", "belinda@example.com", "password", TipoUsuario.ADMIN));
        listaUsuarios.add(new Usuario(2L, "Paula", "Muerte", "paula@example.com", "password", TipoUsuario.ADMIN));
        listaUsuarios.add(new Usuario(2L, "Raquel", "Alvarez", "raquel@example.com", "password", TipoUsuario.ADMIN));


        UsuariosAdapter adapter = new UsuariosAdapter(this, R.layout.item_usuario, listaUsuarios);
        listViewUsuarios.setAdapter(adapter);

        //Llamada al método que se produce cuando se pulsa un elemento de la lista
        listViewUsuarios.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        listaUsuarios.remove(position);
        UsuariosAdapter adapter = new UsuariosAdapter(this, R.layout.item_usuario, listaUsuarios);
        adapter.notifyDataSetChanged();
    }
}
