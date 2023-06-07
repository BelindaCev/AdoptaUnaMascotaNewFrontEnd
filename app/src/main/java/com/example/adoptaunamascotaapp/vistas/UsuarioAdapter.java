package com.example.adoptaunamascotaapp.vistas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adoptaunamascotaapp.R;
import com.example.adoptaunamascotaapp.modelos.Usuario;

import java.util.List;

public class UsuarioAdapter extends ArrayAdapter<Usuario> {

    private Context context;
    private List<Usuario> usuarios;

    public UsuarioAdapter(Context context, List<Usuario> usuarios) {
        super(context, 0, usuarios);
        this.context = context;
        this.usuarios = usuarios;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_lista_item_usuarios, parent, false);
        }

        Usuario usuario = usuarios.get(position);

        ImageView icono = convertView.findViewById(R.id.imageViewUser);
        TextView nombre = convertView.findViewById(R.id.textViewUserName);
        TextView email = convertView.findViewById(R.id.textViewUserMail);

        // Configura el icono, nombre y email del usuario
        icono.setImageResource(R.drawable.baseline_person_24);
        nombre.setText(usuario.getNombre());
        email.setText(usuario.getEmail());

        return convertView;
    }
}

