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
import com.example.adoptaunamascotaapp.modelos.Animal;
import com.example.adoptaunamascotaapp.modelos.Usuario;

import java.util.ArrayList;

public class AnimalesAdminAdapter extends ArrayAdapter {

    Context context;
    int itemAnimalAdmin;

    ArrayList<Animal> animales;

    public AnimalesAdminAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Animal> objects) {
        super(context, resource, objects);
        this.context = context;
        itemAnimalAdmin = resource;
        animales = objects;
    }

    //Método para conseguir la vista (es el 3º de los que ofrece)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            //El false se añade para que no se tenga en cuenta la vista padre para crear la nueva vista (pues la padre no estará creada)
            convertView = layoutInflater.inflate(itemAnimalAdmin, parent, false);
        }

        Animal animal = animales.get(position);

        ImageView icono = convertView.findViewById(R.id.imagenItemAnimalAdmin);
        icono.setImageResource(R.drawable.baseline_delete_24);

        TextView nombre = convertView.findViewById(R.id.nombreItemAnimalAdmin);
        nombre.setText(animal.getNombre());

        TextView categoria = convertView.findViewById(R.id.categoriaItemAnimalAdmin);
        categoria.setText(animal.getCategoria());

        return convertView;
    }
}
