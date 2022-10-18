package edu.puj.talktome.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import edu.puj.talktome.databinding.ActivityNotificacionesTalkerBinding;

public class NotificacionesTalkerActivity extends AppCompatActivity {
    private ActivityNotificacionesTalkerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificacionesTalkerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnPerfilProfesional.setOnClickListener(view -> startActivity(new Intent(this, AvailableProfessionalsActivity.class)));

        binding.btnPerfil.setOnClickListener(view -> startActivity(new Intent(this, PerfilTalkerActivity.class)));

        binding.btnNotification.setOnClickListener( view -> startActivity(new Intent( this, NotificacionesTalkerActivity.class)));
    }

}