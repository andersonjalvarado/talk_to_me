package edu.puj.talktome.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.puj.talktome.R;
import edu.puj.talktome.databinding.ActivityPerfilTalkerBinding;

public class PerfilTalkerActivity extends AppCompatActivity {
    private ActivityPerfilTalkerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPerfilTalkerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //binding.btnPerfilProfesional.setOnClickListener(view -> startActivity(new Intent(this, AvailableProfessionalsActivity.class)));

        //binding.btnPerfil.setOnClickListener(view -> startActivity(new Intent(this, PerfilTalkerActivity.class)));

        //binding.btnNotification.setOnClickListener( view -> startActivity(new Intent( this, NotificacionesTalkerActivity.class)));

        binding.bottomNavigation.setSelectedItemId(R.id.page_Profile);

        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.page_Notification:
                        startActivity(new Intent(getApplicationContext(),NotificacionesTalkerActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_Profile:
                        return true;
                    case R.id.page_Home:
                        startActivity(new Intent(getApplicationContext(),HomeTalkerActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}