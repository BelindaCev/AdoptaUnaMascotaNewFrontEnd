package com.example.adoptaunamascotaapp.vistas;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adoptaunamascotaapp.R;
import com.example.adoptaunamascotaapp.repository.AnimalRepository;
import com.example.adoptaunamascotaapp.repository.GaleriaRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class RegistrarAnimalActivity extends AppCompatActivity {

    public RadioGroup radioGroupCategories;
    public RadioGroup radioGroupSubcategories;
    public Button galeriaButtom;
    public Button camaraButtom;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;

    private ActivityResultLauncher<Intent> galleryActivity;
    private Uri cameraImageUrl;

    private AnimalRepository animalRepository;
    private GaleriaRepository galeriaRepository;
    private ImageView fotoAnimal;
    private Button buttomAgregarAnimal;
    private EditText nombre;
    private DatePicker fechaNacimento;
    private EditText raza;
    private EditText descripcion;
    private RadioButton mayor6Meses;
    private RadioButton menor6Meses;
    private RadioButton pequeno;
    private RadioButton mediano;
    private RadioButton grande;
    private RadioButton gato;
    private RadioButton perro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_animal);

        galeriaButtom = findViewById(R.id.buttonGallery);
        camaraButtom = findViewById(R.id.buttonCamera);
        fotoAnimal = findViewById(R.id.imageView);


        radioGroupCategories = findViewById(R.id.radioGroupCategories);
        radioGroupSubcategories = findViewById(R.id.radioGroupSubcategories);
        buttomAgregarAnimal = findViewById(R.id.buttonSubmit);

        gato = findViewById(R.id.radioButtonCat);
        perro = findViewById(R.id.radioButtonDog);
        mayor6Meses = findViewById(R.id.radioButtonCatOver6Months);
        menor6Meses = findViewById(R.id.radioButtonCatUnder6Months);
        pequeno = findViewById(R.id.radioButtonDogSmall);
        mediano = findViewById(R.id.radioButtonDogMedium);
        grande = findViewById(R.id.radioButtonDogLarge);
        nombre = findViewById(R.id.editTextName);
        fechaNacimento = findViewById(R.id.datePicker1);
        raza = findViewById(R.id.editTextBreed);
        descripcion = findViewById(R.id.editTextDescription);


        galleryActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent data = result.getData();
                    Uri imageUri = data.getData();
                    if (imageUri == null) {
                        // Es de la cámara
                        imageUri = cameraImageUrl;
                    }

                    try (InputStream dis = getContentResolver().openInputStream(imageUri)) {
                        File tempFile = File.createTempFile("aum", String.valueOf(System.currentTimeMillis()));
                        FileOutputStream fos = new FileOutputStream(tempFile);

                        byte[] buf = new byte[8192];
                        int length;
                        while ((length = dis.read(buf)) != -1) {
                            fos.write(buf, 0, length);
                        }

                        fos.close();

                        ImageDecoder.Source source = ImageDecoder.createSource(tempFile);
                        fotoAnimal.setImageBitmap(ImageDecoder.decodeBitmap(source));

                        tempFile.delete();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

        );

        radioGroupCategories.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Mostrar u ocultar las subcategorías según la selección de categoría
                if (checkedId == R.id.radioButtonCat) {
                    radioGroupSubcategories.setVisibility(View.VISIBLE);
                    findViewById(R.id.radioButtonCatUnder6Months).setVisibility(View.VISIBLE);
                    findViewById(R.id.radioButtonCatOver6Months).setVisibility(View.VISIBLE);
                    findViewById(R.id.radioButtonDogSmall).setVisibility(View.GONE);
                    findViewById(R.id.radioButtonDogMedium).setVisibility(View.GONE);
                    findViewById(R.id.radioButtonDogLarge).setVisibility(View.GONE);
                } else if (checkedId == R.id.radioButtonDog) {
                    radioGroupSubcategories.setVisibility(View.VISIBLE);
                    findViewById(R.id.radioButtonCatUnder6Months).setVisibility(View.GONE);
                    findViewById(R.id.radioButtonCatOver6Months).setVisibility(View.GONE);
                    findViewById(R.id.radioButtonDogSmall).setVisibility(View.VISIBLE);
                    findViewById(R.id.radioButtonDogMedium).setVisibility(View.VISIBLE);
                    findViewById(R.id.radioButtonDogLarge).setVisibility(View.VISIBLE);
                } else {
                    radioGroupSubcategories.setVisibility(View.GONE);
                    findViewById(R.id.radioButtonCatUnder6Months).setVisibility(View.GONE);
                    findViewById(R.id.radioButtonCatOver6Months).setVisibility(View.GONE);
                    findViewById(R.id.radioButtonDogSmall).setVisibility(View.GONE);
                    findViewById(R.id.radioButtonDogMedium).setVisibility(View.GONE);
                    findViewById(R.id.radioButtonDogLarge).setVisibility(View.GONE);
                }
            }

        });
        galeriaButtom.setOnClickListener(v -> {
            openGallery();
        });
        camaraButtom.setOnClickListener(v -> {
            openCamera();
        });

        buttomAgregarAnimal.setOnClickListener(v -> {

            if (gato.isActivated()) {
                if (mayor6Meses.isActivated()) {

                } else if (menor6Meses.isActivated()) {

                } else {
                    Toast.makeText(RegistrarAnimalActivity.this, "eliga subcategoria", Toast.LENGTH_SHORT).show();
                }
            } else if (perro.isActivated()) {
                if (pequeno.isActivated()) {

                } else if (mediano.isActivated()) {

                } else if (grande.isActivated()) {

                } else {
                    Toast.makeText(RegistrarAnimalActivity.this, "eliga subcategoria", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(RegistrarAnimalActivity.this, "eliga categoria", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "AUM_Capture");
        cameraImageUrl = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUrl);

        galleryActivity.launch(intent);
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        galleryActivity.launch(intent);
    }

    private boolean validarRadioButtoms() {
        if (!gato.isActivated() && !perro.isActivated()) {
            Toast.makeText(RegistrarAnimalActivity.this, "eliga categoria", Toast.LENGTH_SHORT).show();
            return false;
        } else if (gato.isActivated()) {
            if (!mayor6Meses.isActivated() && !menor6Meses.isActivated()) {
                Toast.makeText(RegistrarAnimalActivity.this, "eliga subcategoria", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        } else {
            if (!pequeno.isActivated() && !mediano.isActivated() && !grande.isActivated()) {
                Toast.makeText(RegistrarAnimalActivity.this, "eliga subcategoria", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }
    }

    private boolean validarCampos() {
        if (nombre.getText().toString().isEmpty()) {
            Toast.makeText(RegistrarAnimalActivity.this, "Escriba el nombre del animal", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
