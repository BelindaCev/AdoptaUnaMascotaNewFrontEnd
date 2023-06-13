package com.example.adoptaunamascotaapp.vistas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adoptaunamascotaapp.R;
import com.example.adoptaunamascotaapp.modelos.SolicitudAdopcion;
import com.example.adoptaunamascotaapp.repository.SessionManager;
import com.example.adoptaunamascotaapp.repository.SolicitudRepository;

import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolicitudAdopcionActivity extends AppCompatActivity {

    private EditText editTextTelefono;
    private  EditText editTextFechaNacimiento;
    private  EditText editTextDireccion;
    private  EditText editTextMensaje;
    private Button buttonEnviar;

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

        solicitudRepository = new SolicitudRepository();

        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarYenviarSolicitud();
            }
        });
    }

    private void validarYenviarSolicitud(){
        String telefono = editTextTelefono.getText().toString().trim();
        String fechaNacimiento = editTextFechaNacimiento.getText().toString().trim();
        String direccion = editTextDireccion.getText().toString().trim();
        String mensaje = editTextMensaje.getText().toString().trim();
        LocalDateTime fechaActual = LocalDateTime.now();
        LocalDateTime fechaNacimientoDateTime = LocalDateTime.parse(fechaNacimiento);

        if (telefono.isEmpty()) {
            Toast.makeText(this, "Debe completar el teléfono", Toast.LENGTH_SHORT).show();
        } else if (fechaNacimiento.isEmpty()) {
            Toast.makeText(this, "Debe completar la fecha de nacimiento", Toast.LENGTH_SHORT).show();
        } else if (direccion.isEmpty()) {
            Toast.makeText(this, "Debe completar la dirección", Toast.LENGTH_SHORT).show();
        } else if (mensaje.isEmpty()) {
            Toast.makeText(this, "Debe completar el mensaje", Toast.LENGTH_SHORT).show();
        } else if (fechaNacimientoDateTime.isAfter(fechaActual.minusYears(18))) {
            Toast.makeText(this, "Debes ser mayor de edad para enviar la solicitud", Toast.LENGTH_SHORT).show();
        } else {
            long idAnimal = getIntent().getLongExtra("idAnimal", -1);
            long idUsuario = SessionManager.getInstance().getUserId();
            SolicitudAdopcion solicitudAdopcion = new SolicitudAdopcion(idAnimal, idUsuario, telefono, mensaje, LocalDateTime.parse(fechaNacimiento), direccion);

            solicitudRepository.registerSolicitud(solicitudAdopcion, new Callback<SolicitudAdopcion>() {
                @Override
                public void onResponse(Call<SolicitudAdopcion> call, Response<SolicitudAdopcion> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(SolicitudAdopcionActivity.this, "Solicitud enviada con éxito", Toast.LENGTH_SHORT).show();
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
    }
}
