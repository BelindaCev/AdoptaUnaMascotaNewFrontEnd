package com.example.adoptaunamascotaapp.vistas;

import static com.example.adoptaunamascotaapp.R.id.listaUsuarios;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adoptaunamascotaapp.R;
import com.example.adoptaunamascotaapp.modelos.Usuario;
import com.example.adoptaunamascotaapp.tipos.TipoUsuario;

import java.util.ArrayList;
import java.util.List;

public class ListaUsuarios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_item_usuarios);

        ListView listView = findViewById(R.id.listaUsuarios);

        List<Usuario> listUsuarios = new ArrayList<>();
        listUsuarios.add(new Usuario(1L, "Belinda", "Cuesta", "belinda@example.com", "password", TipoUsuario.ADMIN));
        listUsuarios.add(new Usuario(2L, "Paula", "Muerte", "paula@example.com", "password", TipoUsuario.ADMIN));
        listUsuarios.add(new Usuario(2L, "Raquel", "Alvarez", "raquel@example.com", "password", TipoUsuario.ADMIN));

        UsuarioAdapter adapter = new UsuarioAdapter(this, listUsuarios);
        listView.setAdapter(adapter);
    }
}
