package edu.puj.talktome.activities;

import androidx.appcompat.app.AppCompatActivity;
import edu.puj.talktome.databinding.ActivityPerfilProfesional2Binding;

import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

public class PerfilProfesional2Activity extends AppCompatActivity {

    private ActivityPerfilProfesional2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPerfilProfesional2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}