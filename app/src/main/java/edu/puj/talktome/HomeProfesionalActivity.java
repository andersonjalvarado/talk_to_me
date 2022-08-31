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

        binding.botonLista.setOnClickListener(view -> startActivity(new Intent(this, SolicitudesActivity.class)));
    }
}