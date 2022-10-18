package edu.puj.talktome.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import edu.puj.talktome.databinding.ActivityAppointmentsDetailsTalkerBinding;

public class AppointmentsDetailsTalkerActivity extends AppCompatActivity {
    private ActivityAppointmentsDetailsTalkerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppointmentsDetailsTalkerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnUbicacion.setOnClickListener(view -> startActivity(new Intent(this, LocationsTalkerActivity.class)));

        binding.btnPerfilProfesional.setOnClickListener(view -> startActivity(new Intent(this, AvailableProfessionalsActivity.class)));

        binding.btnPerfil.setOnClickListener(view -> startActivity(new Intent(this, PerfilTalkerActivity.class)));

        binding.btnNotification.setOnClickListener(view -> startActivity(new Intent(this, NotificacionesTalkerActivity.class)));

    }
}