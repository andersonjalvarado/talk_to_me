package edu.puj.talktome.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.puj.talktome.databinding.ActivityDiagnosesTalkerBinding;

public class DiagnosesTalkerActivity extends AppCompatActivity {
    private ActivityDiagnosesTalkerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDiagnosesTalkerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnPerfilProfesional.setOnClickListener(view -> startActivity(new Intent(this, AvailableProfessionalsActivity.class)));

        binding.btnPerfil.setOnClickListener(view -> startActivity(new Intent(this, PerfilTalkerActivity.class)));

        binding.btnNotification.setOnClickListener( view -> startActivity(new Intent( this, NotificacionesTalkerActivity.class)));

    }


    public void onClick(View view) {
        startActivity(new Intent(this, DiagnosticDetailsActivity.class));
    }
}