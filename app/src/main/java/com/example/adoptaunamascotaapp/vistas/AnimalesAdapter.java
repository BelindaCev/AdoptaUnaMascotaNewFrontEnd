package com.example.adoptaunamascotaapp.vistas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adoptaunamascotaapp.R;
import com.example.adoptaunamascotaapp.modelos.Animal;

import java.util.List;

public class AnimalesAdapter extends ArrayAdapter<Animal> {

    private Context context;
    private List<Animal> animalList;

    public AnimalesAdapter(Context context, List<Animal> animalList) {
        super(context, 0, animalList);
        this.context = context;
        this.animalList = animalList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_animal, parent, false);
        }

        Animal animal = animalList.get(position);

        ImageView imageViewAnimal = convertView.findViewById(R.id.imagenItemAnimal);
        TextView textViewAnimalName = convertView.findViewById(R.id.nombreItemAnimal);
        TextView textViewAnimalDescription = convertView.findViewById(R.id.descripcionItemAnimal);

        // Configurar la imagen, nombre y descripción del animal
        imageViewAnimal.setImageResource(R.drawable.icono_animales);
        textViewAnimalName.setText(animal.getNombre());
        textViewAnimalDescription.setText(animal.getDescripcion());

        return convertView;
    }

    public void actualizarLista(List<Animal> nuevosAnimales) {
        animalList.clear();
        animalList.addAll(nuevosAnimales);
        notifyDataSetChanged();
    }
}