package com.example.adoptaunamascotaapp.vistas;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.adoptaunamascotaapp.R;
import com.example.adoptaunamascotaapp.modelos.Animal;

import java.util.List;

public class ListaAdapter extends ArrayAdapter<Animal> {
    private Context context;
    private int layoutResourceId;
    private List<Animal> data;

    public ListaAdapter(Context context, int layoutResourceId, List<Animal> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        AnimalHolder holder;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new AnimalHolder();
            holder.imageViewAnimal = row.findViewById(R.id.imageViewAnimal);
            holder.textViewAnimalName = row.findViewById(R.id.textViewAnimalName);
            holder.textViewAnimalDescription = row.findViewById(R.id.textViewAnimalDescription);

            row.setTag(holder);
        } else {
            holder = (AnimalHolder) row.getTag();
        }

        Animal animal = data.get(position);
        holder.textViewAnimalName.setText(animal.getNombre());
        holder.textViewAnimalDescription.setText(animal.getDescripcion());

        // Cargar la imagen utilizando alguna biblioteca como Picasso o Glide
        // Picasso.get().load(animal.getImageUrl()).into(holder.imageViewAnimal);

        return row;
    }

    static class AnimalHolder {
        ImageView imageViewAnimal;
        TextView textViewAnimalName;
        TextView textViewAnimalDescription;
    }
}