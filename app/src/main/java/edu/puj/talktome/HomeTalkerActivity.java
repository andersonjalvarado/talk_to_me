package edu.puj.talktome;

import androidx.appcompat.app.AppCompatActivity;
import edu.puj.talktome.databinding.ActivityHomeTalkerBinding;

import android.content.Intent;
import android.os.Bundle;

public class HomeTalkerActivity extends AppCompatActivity {

    private ActivityHomeTalkerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeTalkerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnPerfilProfesional.setOnClickListener(view -> startActivity(new Intent(this, PerfilProfesional2Activity.class)));

        binding.btnUbicacion.setOnClickListener(view -> startActivity(new Intent(this, LocationsTalkerActivity.class)));

        //Falta el calendario, lo miro más tarde
        binding.btnCita.setOnClickListener(view -> startActivity(new Intent(this, AppointmentsTalkerActivity.class)));

        binding.btnDiagnostico.setOnClickListener(view -> startActivity(new Intent(this, DiagnosesTalkerActivity.class)));

    }
}