package edu.puj.talktome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.puj.talktome.databinding.ActivityAvailableProfessionalsBinding;
import edu.puj.talktome.databinding.ActivityCertificatesBinding;

public class AvailableProfessionalsActivity extends AppCompatActivity {
    private ActivityAvailableProfessionalsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAvailableProfessionalsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnPerfilProfesional.setOnClickListener(view -> startActivity(new Intent(this, AvailableProfessionalsActivity.class)));

        binding.btnPerfil.setOnClickListener(view -> startActivity(new Intent(this, PerfilTalkerActivity.class)));

        binding.btnNotification.setOnClickListener( view -> startActivity(new Intent( this, NotificacionesTalkerActivity.class)));
    }

    public void onClick(View view) {
        startActivity(new Intent(this, PerfilProfesional2Activity.class));
    }
}