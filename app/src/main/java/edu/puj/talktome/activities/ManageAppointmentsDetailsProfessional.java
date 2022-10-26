package edu.puj.talktome.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import edu.puj.talktome.R;
import edu.puj.talktome.databinding.ActivityAppointmentsDetailsProfessionalBinding;
import edu.puj.talktome.databinding.ActivityManageAppointmentsDetailsProfessionalBinding;

public class ManageAppointmentsDetailsProfessional extends AppCompatActivity {
    private ActivityManageAppointmentsDetailsProfessionalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageAppointmentsDetailsProfessionalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.page_Notification:
                        startActivity(new Intent(getApplicationContext(),NotificacionesProfesionalActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_Home:
                        startActivity(new Intent(getApplicationContext(),HomeProfesionalActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_Profile:
                        startActivity(new Intent(getApplicationContext(),PerfilProfesionalActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.logoutButton:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                return false;
            }
        });
    }
}