package com.example.adoptaunamascotaapp.vistas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adoptaunamascotaapp.R;
import com.example.adoptaunamascotaapp.modelos.Animal;
import com.example.adoptaunamascotaapp.modelos.Galeria;
import com.example.adoptaunamascotaapp.repository.GaleriaRepository;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimalesAdapter extends ArrayAdapter<Animal> {

    private Context context;
    private List<Animal> animalList;
    private final GaleriaRepository galeriaRepository = new GaleriaRepository();

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
        textViewAnimalName.setText(animal.getNombre());
        textViewAnimalDescription.setText(animal.getDescripcion());

        // Obtener el ID del animal
        long idAnimal = animal.getId();

        // Llamar al método para cargar la foto del animal
        cargarFotoAnimal(idAnimal, imageViewAnimal);

        // Resto del código existente

        return convertView;
    }

    public void actualizarLista(List<Animal> nuevosAnimales) {
        animalList.clear();
        animalList.addAll(nuevosAnimales);
        notifyDataSetChanged();
    }

    private void cargarFotoAnimal(long idAnimal, ImageView imageView) {
        GaleriaRepository galeriaRepository = new GaleriaRepository();
        galeriaRepository.getGaleria(new Callback<List<Galeria>>() {
            @Override
            public void onResponse(Call<List<Galeria>> call, Response<List<Galeria>> response) {
                if (response.isSuccessful()) {
                    List<Galeria> galeriaList = response.body();
                    if (galeriaList != null && !galeriaList.isEmpty()) {
                        Galeria galeria = galeriaList.get(0); // Obtener la primera galería (puedes ajustarlo según tu lógica)

                        // Obtener la ruta de la foto del animal
                        String rutaFoto = galeria.getRutaFoto();

                        System.out.println(rutaFoto);

                        // Cargar la foto utilizando una biblioteca de manejo de imágenes (como Picasso, Glide, etc.)
                        // Aquí, se muestra un ejemplo utilizando Picasso
                        //Picasso.get().load(rutaFoto).into(imageView);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Galeria>> call, Throwable t) {
                // Manejar el error en la respuesta del servidor
            }
        }, idAnimal);
    }
}