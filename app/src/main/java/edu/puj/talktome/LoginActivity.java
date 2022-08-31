package edu.puj.talktome;

import androidx.appcompat.app.AppCompatActivity;
import edu.puj.talktome.databinding.ActivityLoginBinding;

import android.content.Intent;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnInicioSesion.setOnClickListener(view -> startActivity(new Intent(this, HomeTalkerActivity.class)));
        binding.registrateTextView.setOnClickListener(view -> startActivity(new Intent(this, RoleActivity.class)));
    }
}