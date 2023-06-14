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
import com.example.adoptaunamascotaapp.modelos.Animal;
import com.example.adoptaunamascotaapp.repository.AnimalRepository;
import com.example.adoptaunamascotaapp.repository.GaleriaRepository;
import com.example.adoptaunamascotaapp.tipos.SexoEnum;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private DatePicker fechaNacimentoDP;
    private EditText raza;
    private EditText descripcion;
    private RadioButton mayor6Meses;
    private RadioButton menor6Meses;
    private RadioButton pequeno;
    private RadioButton mediano;
    private RadioButton grande;
    private RadioButton gato;
    private RadioButton perro;
    private RadioButton hembra;
    private RadioButton macho;
    private String categoria;
    private String subcategoria;
    private SexoEnum sexo;
    private File imagenAnimal;


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
        fechaNacimentoDP = findViewById(R.id.datePicker1);
        raza = findViewById(R.id.editTextBreed);
        descripcion = findViewById(R.id.editTextDescription);
        hembra = findViewById(R.id.radioButtonHembra);
        macho = findViewById(R.id.radioButtonMacho);


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
                        imagenAnimal = tempFile;
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

            if (validarCampos() && validarRadioButtoms()) {
                if (hembra.isChecked()) {
                    sexo = SexoEnum.HEMBRA;
                } else {
                    sexo = SexoEnum.MACHO;
                }
                if (gato.isChecked()) {
                    categoria = "Gato";
                    if (mayor6Meses.isChecked()) {
                        subcategoria = "Mayor de 6 meses";
                    } else {
                        subcategoria = "Menor de 6 meses";
                    }
                } else {
                    categoria = "Perro";
                    if (pequeno.isChecked()) {
                        subcategoria = "Pequeño";
                    } else if (mediano.isChecked()) {
                        subcategoria = "Mediano";
                    } else {
                        subcategoria = "Grande";
                    }
                }
                registrarAnimal(categoria, subcategoria, sexo);
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
        if (!(hembra.isChecked()) && !(macho.isChecked())) {
            Toast.makeText(RegistrarAnimalActivity.this, "elija sexo del animal", Toast.LENGTH_SHORT).show();
            return false;
        } else {


            if (!(gato.isChecked()) && !(perro.isChecked())) {
                Toast.makeText(RegistrarAnimalActivity.this, "eliga categoria", Toast.LENGTH_SHORT).show();
                return false;
            } else if (gato.isChecked()) {
                if (!mayor6Meses.isChecked() && !menor6Meses.isChecked()) {
                    Toast.makeText(RegistrarAnimalActivity.this, "eliga subcategoria", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    return true;
                }
            } else {
                if (!pequeno.isChecked() && !mediano.isChecked() && !grande.isChecked()) {
                    Toast.makeText(RegistrarAnimalActivity.this, "eliga subcategoria", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    private boolean validarCampos() {
        LocalDate fechaNacimiento = capturaFechaNacimiento(fechaNacimentoDP);
        if (nombre.getText().toString().isEmpty()) {
            Toast.makeText(RegistrarAnimalActivity.this, "Escriba el nombre del animal", Toast.LENGTH_SHORT).show();
            return false;
        } else if (descripcion.getText().toString().isEmpty()) {
            Toast.makeText(RegistrarAnimalActivity.this, "Escriba la descripción", Toast.LENGTH_SHORT).show();
            return false;
        } else if (raza.getText().toString().isEmpty()) {
            Toast.makeText(RegistrarAnimalActivity.this, "Escriba la raza", Toast.LENGTH_SHORT).show();
            return false;
        } else if (LocalDate.now().isBefore(fechaNacimiento)) {
            Toast.makeText(RegistrarAnimalActivity.this, "La fecha es incorrecta", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }


    }

    /**
     * @param fechaNacimiento
     * @return a java.util.Date
     */
    public static LocalDate capturaFechaNacimiento(DatePicker fechaNacimiento) {
        int day = fechaNacimiento.getDayOfMonth();
        int month = fechaNacimiento.getMonth();
        int year = fechaNacimiento.getYear();

        // Crear un objeto LocalDate con los valores capturados
        LocalDate fechaNac = LocalDate.of(year, month, day);

        return fechaNac;
    }

    private void registrarAnimal(String categoria, String subcategoria, SexoEnum sexo) {
        LocalDate fechaNacimiento = capturaFechaNacimiento(fechaNacimentoDP);
        Animal animal = new Animal(
                1L, // Cambiar el valor del ID según corresponda
                categoria,
                subcategoria,
                nombre.getText().toString(),
                fechaNacimiento,
                sexo,
                raza.getText().toString(),
                descripcion.getText().toString()
        );
        animalRepository.createAnimal(animal, new Callback<Animal>() {

            @Override
            public void onResponse(Call<Animal> call, Response<Animal> response) {
                Toast.makeText(RegistrarAnimalActivity.this, "Animal dado de alta exitosamente", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    Animal animal = response.body();
                    registrarGaleria(animal.getId());
                    Intent intent = new Intent(RegistrarAnimalActivity.this, ListaAnimalesAdminActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegistrarAnimalActivity.this, "Error en el alta del Animal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Animal> call, Throwable t) {
                Toast.makeText(RegistrarAnimalActivity.this, "Error en el alta del Animal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registrarGaleria(Long idAnimal) {
        galeriaRepository.createGaleria(idAnimal, imagenAnimal);
    }
}
