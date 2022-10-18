package edu.puj.talktome.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import edu.puj.talktome.databinding.ActivityNotificacionesProfesionalBinding;

public class NotificacionesProfesionalActivity extends AppCompatActivity {
    private ActivityNotificacionesProfesionalBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificacionesProfesionalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnPerfilProfesional.setOnClickListener(view -> startActivity(new Intent(this, AvailableProfessionalsActivity.class)));

        binding.btnPerfil.setOnClickListener(view -> startActivity(new Intent(this, PerfilTalkerActivity.class)));

        binding.btnNotification.setOnClickListener( view -> startActivity(new Intent( this, NotificacionesTalkerActivity.class)));
    }

}