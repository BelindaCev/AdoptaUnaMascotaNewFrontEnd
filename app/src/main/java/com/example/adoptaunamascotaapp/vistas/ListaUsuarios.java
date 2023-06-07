package com.example.adoptaunamascotaapp.vistas;

import static com.example.adoptaunamascotaapp.R.id.listaUsuarios;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adoptaunamascotaapp.R;
import com.example.adoptaunamascotaapp.modelos.Usuario;
import com.example.adoptaunamascotaapp.tipos.TipoUsuario;

import java.util.ArrayList;
import java.util.List;

public class ListaUsuarios extends AppCompatActivity implements AdapterView.OnItemClickListener {

    List<Usuario> listUsuarios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_item_usuarios);

        ListView listView = findViewById(R.id.listaUsuarios);

        listUsuarios.add(new Usuario(1L, "Belinda", "Cuesta", "belinda@example.com", "password", TipoUsuario.ADMIN));
        listUsuarios.add(new Usuario(2L, "Paula", "Muerte", "paula@example.com", "password", TipoUsuario.ADMIN));
        listUsuarios.add(new Usuario(2L, "Raquel", "Alvarez", "raquel@example.com", "password", TipoUsuario.ADMIN));

        UsuarioAdapter adapter = new UsuarioAdapter(this, listUsuarios);
        listView.setAdapter(adapter);

        //Llamada al método que se produce cuando se pulsa un elemento de la lista
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        listUsuarios.remove(position);
        UsuarioAdapter adapter = new UsuarioAdapter(this, listUsuarios);
        adapter.notifyDataSetChanged();
    }
}
