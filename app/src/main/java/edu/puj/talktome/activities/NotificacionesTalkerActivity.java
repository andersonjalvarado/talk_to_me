package edu.puj.talktome.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.puj.talktome.R;
import edu.puj.talktome.databinding.ActivityNotificacionesTalkerBinding;

public class NotificacionesTalkerActivity extends AppCompatActivity {
    private ActivityNotificacionesTalkerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificacionesTalkerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //binding.btnPerfilProfesional.setOnClickListener(view -> startActivity(new Intent(this, AvailableProfessionalsActivity.class)));

        //binding.btnPerfil.setOnClickListener(view -> startActivity(new Intent(this, PerfilTalkerActivity.class)));

        //binding.btnNotification.setOnClickListener( view -> startActivity(new Intent( this, NotificacionesTalkerActivity.class)));

        binding.bottomNavigation.setSelectedItemId(R.id.page_Notification);

        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.page_Home:
                        startActivity(new Intent(getApplicationContext(),HomeTalkerActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_Notification:
                        return true;
                    case R.id.page_Profile:
                        startActivity(new Intent(getApplicationContext(),PerfilTalkerActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

}