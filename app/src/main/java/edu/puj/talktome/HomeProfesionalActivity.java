package edu.puj.talktome;

import androidx.appcompat.app.AppCompatActivity;

import edu.puj.talktome.databinding.ActivityCertificatesBinding;
import edu.puj.talktome.databinding.ActivityHomeProfesionalBinding;

import android.content.Intent;
import android.os.Bundle;

public class HomeProfesionalActivity extends AppCompatActivity {

    private ActivityHomeProfesionalBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeProfesionalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCertificados.setOnClickListener(view -> startActivity(new Intent(this, CertificatesActivity.class)));

        binding.btnRequest.setOnClickListener(view -> startActivity(new Intent(this, SolicitudesActivity.class)));

        binding.btnCita.setOnClickListener(view -> startActivity(new Intent(this, AppointmentsProfesionalActivity.class)));

        binding.btnPatients.setOnClickListener(view -> startActivity(new Intent(this, ListTalkerActivity.class)));

        binding.btnPerfil.setOnClickListener(view -> startActivity(new Intent(this, PerfilProfesionalActivity.class)));

        binding.btnListaPacientes.setOnClickListener(view -> startActivity(new Intent(this, ListTalkerActivity.class)));
        
    }
}