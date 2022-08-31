package edu.puj.talktome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import edu.puj.talktome.databinding.ActivityAppointmentsDetailsProfessionalBinding;
import edu.puj.talktome.databinding.ActivityLoginBinding;

public class AppointmentsDetailsProfessional extends AppCompatActivity {
    private ActivityAppointmentsDetailsProfessionalBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppointmentsDetailsProfessionalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnEditCita.setOnClickListener(view -> startActivity(new Intent(this, ManageAppointmentsDetailsProfessional.class)));
    }
}