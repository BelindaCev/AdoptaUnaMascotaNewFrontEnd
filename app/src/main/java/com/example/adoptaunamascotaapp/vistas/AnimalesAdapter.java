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

import com.example.adoptaunamascotaapp.R;
import com.example.adoptaunamascotaapp.modelos.Animal;
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


        if (animal.getCategoria().equals("gato")){
            imageViewAnimal.setImageResource(R.drawable.icono_gato);
        }else{
            imageViewAnimal.setImageResource(R.drawable.icono_perro);
        }
        textViewAnimalName.setText(animal.getNombre());
        textViewAnimalDescription.setText(animal.getDescripcion());


        // Configurar la imagen, nombre y descripción del animal
        galeriaRepository.getFoto(animal.getId(), new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try (InputStream inputStream = response.body().byteStream()) {
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageViewAnimal.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        return convertView;
    }

    public void actualizarLista(List<Animal> nuevosAnimales) {
        animalList.clear();
        animalList.addAll(nuevosAnimales);
        notifyDataSetChanged();
    }
}