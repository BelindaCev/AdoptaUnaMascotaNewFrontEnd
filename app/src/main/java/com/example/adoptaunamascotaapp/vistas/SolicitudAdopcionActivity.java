package com.example.adoptaunamascotaapp.vistas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adoptaunamascotaapp.R;
import com.example.adoptaunamascotaapp.modelos.SolicitudAdopcion;
import com.example.adoptaunamascotaapp.repository.GaleriaRepository;
import com.example.adoptaunamascotaapp.repository.SessionManager;
import com.example.adoptaunamascotaapp.repository.SolicitudRepository;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolicitudAdopcionActivity extends AppCompatActivity implements View.OnClickListener {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private EditText editTextTelefono;
    private EditText editTextFechaNacimiento;
    private EditText editTextDireccion;
    private EditText editTextMensaje;

    private ImageView irHome;
    private Button buttonEnviar;
    private TextView textViewNombreAnimal;
    TextView textViewDescripcionAnimal;
    GaleriaRepository galeriaRepository;
    ImageView imageViewAnimal;


    private SolicitudRepository solicitudRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_adopcion);

        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextFechaNacimiento = findViewById(R.id.editTextFechaNacimiento);
        editTextDireccion = findViewById(R.id.editTextDireccion);
        editTextMensaje = findViewById(R.id.editTextMensaje);
        buttonEnviar = findViewById(R.id.buttonEnviar);
        galeriaRepository = new GaleriaRepository();
        imageViewAnimal = findViewById(R.id.imageViewAnimal);
        irHome = findViewById(R.id.irHome);

        irHome.setOnClickListener(this);

        Intent intent = getIntent();
        String nombreAnimal = intent.getStringExtra("nombreAnimal");
        String descripcionAnimal = intent.getStringExtra("descripcionAnimal");
        String categoria = intent.getStringExtra("categoria");

        if (categoria.equals("gato")){
            imageViewAnimal.setImageResource(R.drawable.icono_gato);
        }else{
            imageViewAnimal.setImageResource(R.drawable.icono_perro);
        }

        galeriaRepository.getFoto(intent.getLongExtra("idAnimal", 1), new Callback<ResponseBody>() {
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
        textViewNombreAnimal = findViewById(R.id.textViewAnimalName);
        textViewDescripcionAnimal = findViewById(R.id.textViewDescription);
        textViewNombreAnimal.setText(nombreAnimal);
        textViewDescripcionAnimal.setText(descripcionAnimal);


        solicitudRepository = new SolicitudRepository();

        editTextFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SolicitudAdopcion solicitudAdopcion = convertirSolicitud();
                if (validarYenviarSolicitud(solicitudAdopcion)) {
                    enviarSolicitud(solicitudAdopcion);
                }
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            String selectedDate = String.format(Locale.getDefault(), "%02d-%02d-%04d", dayOfMonth, month + 1, year);
            editTextFechaNacimiento.setText(selectedDate);
        };

        // Obtiene la fecha actual para establecerla como fecha predeterminada en el diálogo
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Crea el diálogo del selector de fecha y muestra el calendario
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    private SolicitudAdopcion convertirSolicitud() {
        String telefono = editTextTelefono.getText().toString();
        String fechaNacimiento = editTextFechaNacimiento.getText().toString();
        String direccion = editTextDireccion.getText().toString();
        String mensaje = editTextMensaje.getText().toString();

        long idAnimal = getIntent().getLongExtra("idAnimal", -1);
        long idUsuario = SessionManager.getInstance().getUserId();

        LocalDate fechaNacimientoDate = null;
        if (!fechaNacimiento.isEmpty()) {
            fechaNacimientoDate = LocalDate.parse(fechaNacimiento, FORMATTER);
        }

        return new SolicitudAdopcion(idAnimal, idUsuario, telefono, mensaje, fechaNacimientoDate, direccion);
    }

    private boolean validarYenviarSolicitud(SolicitudAdopcion solicitudAdopcion) {
        String telefono = solicitudAdopcion.getTelefono();
        String direccion = solicitudAdopcion.getDomicilio();
        String mensaje = solicitudAdopcion.getDetalleSolicitud();

        if (telefono.isEmpty() || direccion.isEmpty() || mensaje.isEmpty()) {
            Toast.makeText(SolicitudAdopcionActivity.this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        } else if (telefono.length() != 9 || telefono.matches("[0-9]")){
            Toast.makeText(SolicitudAdopcionActivity.this, "El teléfono debe contener 9 números", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            LocalDate fechaNacimientoDate = solicitudAdopcion.getEdad();
            LocalDate fechaActual = LocalDate.now();
            int edad = fechaActual.getYear() - fechaNacimientoDate.getYear();
            if (edad <= 18) {
                Toast.makeText(getApplicationContext(), "Debes ser mayor de edad", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }
    }

    private void enviarSolicitud(SolicitudAdopcion solicitudAdopcion) {
        solicitudRepository.registerSolicitud(solicitudAdopcion, new Callback<SolicitudAdopcion>() {
            @Override
            public void onResponse(Call<SolicitudAdopcion> call, Response<SolicitudAdopcion> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SolicitudAdopcionActivity.this, "Solicitud enviada con éxito", Toast.LENGTH_SHORT).show();
                    redirigirListadoGeneral();
                } else {
                    Toast.makeText(SolicitudAdopcionActivity.this, "Error al enviar la solicitud", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SolicitudAdopcion> call, Throwable t) {
                Toast.makeText(SolicitudAdopcionActivity.this, "Error en la solicitud: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public  void redirigirListadoGeneral() {
        Intent intent = new Intent(SolicitudAdopcionActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(SolicitudAdopcionActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
