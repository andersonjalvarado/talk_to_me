package edu.puj.talktome;

import androidx.appcompat.app.AppCompatActivity;
import edu.puj.talktome.databinding.ActivityPerfilProfesional2Binding;
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