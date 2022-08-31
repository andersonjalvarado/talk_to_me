package edu.puj.talktome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import edu.puj.talktome.databinding.ActivityAppointmentsDetailsProfessionalBinding;
import edu.puj.talktome.databinding.ActivityManageAppointmentsDetailsProfessionalBinding;

public class ManageAppointmentsDetailsProfessional extends AppCompatActivity {
    private ActivityManageAppointmentsDetailsProfessionalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageAppointmentsDetailsProfessionalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}