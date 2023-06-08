package com.example.adoptaunamascotaapp.vistas;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.adoptaunamascotaapp.R;
import com.example.adoptaunamascotaapp.modelos.Usuario;

import java.util.ArrayList;

public class UsuariosAdapter extends ArrayAdapter {

    Context context;
    int itemUsuarios;
    ArrayList<Usuario> usuarios;

    public UsuariosAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Usuario> objects) {
        super(context, resource, objects);
        this.context = context;
        itemUsuarios = resource;
        usuarios = objects;
    }

    //Método para conseguir la vista (es el 3º de los que ofrece)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            //El false se añade para que no se tenga en cuenta la vista padre para crear la nueva vista (pues la padre no estará creada)
            convertView = layoutInflater.inflate(itemUsuarios, parent, false);
        }

        Usuario usuario = usuarios.get(position);

        ImageView icono = convertView.findViewById(R.id.imagenItemUsuario);
        icono.setImageResource(R.drawable.baseline_delete_24);

        TextView nombre = convertView.findViewById(R.id.nombreItemUsuario);
        nombre.setText(usuario.getNombre());

        TextView email = convertView.findViewById(R.id.emailItemUsuario);
        email.setText(usuario.getEmail());

        return convertView;
    }
}
