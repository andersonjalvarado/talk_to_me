package edu.puj.talktome.activities;

import androidx.appcompat.app.AppCompatActivity;

import edu.puj.talktome.R;
import edu.puj.talktome.databinding.ActivityRegistroBinding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroActivity extends AppCompatActivity {

    private ActivityRegistroBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Cambiar nombre
        binding.textName.setText(getIntent().getStringExtra("nombre"));
        short valor = Short.parseShort(getIntent().getStringExtra("valor"));

        //Animaciones
        Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation animacion2 = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_abajo);

        binding.faceView.setAnimation(animacion1);
        binding.textName.setAnimation(animacion1);
        binding.textParte.setAnimation(animacion2);
        binding.nombre.setAnimation(animacion2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (valor == 1)
                    startActivity(new Intent(RegistroActivity.this,HomeTalkerActivity.class));
                else
                    startActivity(new Intent(RegistroActivity.this,HomeProfesionalActivity.class));

                finish();
            }
        },4000);
    }
}