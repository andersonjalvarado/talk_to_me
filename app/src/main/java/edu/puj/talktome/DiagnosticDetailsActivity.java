package edu.puj.talktome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import edu.puj.talktome.databinding.ActivityDiagnosesTalkerBinding;
import edu.puj.talktome.databinding.ActivityDiagnosticDetailsBinding;

public class DiagnosticDetailsActivity extends AppCompatActivity {
    private ActivityDiagnosticDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDiagnosticDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnPerfilProfesional.setOnClickListener(view -> startActivity(new Intent(this, AvailableProfessionalsActivity.class)));

        binding.btnPerfil.setOnClickListener(view -> startActivity(new Intent(this, PerfilTalkerActivity.class)));

        binding.btnNotification.setOnClickListener( view -> startActivity(new Intent( this, NotificacionesTalkerActivity.class)));
    }
}