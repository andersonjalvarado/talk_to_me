package edu.puj.talktome.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import edu.puj.talktome.databinding.ActivityAppointmentsDetailsProfessionalBinding;

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