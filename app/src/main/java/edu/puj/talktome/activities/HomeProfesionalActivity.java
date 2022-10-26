package edu.puj.talktome.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import edu.puj.talktome.R;
import edu.puj.talktome.databinding.ActivityHomeProfesionalBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeProfesionalActivity extends AppCompatActivity {

    private ActivityHomeProfesionalBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeProfesionalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCertificados.setOnClickListener(view -> startActivity(new Intent(this, CertificatesActivity.class)));

        binding.btnRequest.setOnClickListener(view -> startActivity(new Intent(this, SolicitudesActivity.class)));

        binding.btnCita.setOnClickListener(view -> startActivity(new Intent(this, AppointmentsProfesionalActivity.class)));

        binding.btnPatients.setOnClickListener(view -> startActivity(new Intent(this, ListTalkerActivity.class)));

        //binding.btnPerfil.setOnClickListener(view -> startActivity(new Intent(this, PerfilProfesionalActivity.class)));

        //binding.btnListaPacientes.setOnClickListener(view -> startActivity(new Intent(this, ListTalkerActivity.class)));

        //binding.btnNotification.setOnClickListener(view -> startActivity(new Intent(this, NotificacionesProfesionalActivity.class)));
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