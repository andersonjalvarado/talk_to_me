package edu.puj.talktome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import edu.puj.talktome.databinding.ActivityLoginBinding;

import android.content.Intent;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {
    private @NonNull ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.registrateTextView.setOnClickListener(view -> startActivity(new Intent(this, RoleActivity.class)));
    }
}