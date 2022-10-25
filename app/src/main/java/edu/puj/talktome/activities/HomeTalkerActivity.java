package edu.puj.talktome.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import edu.puj.talktome.R;
import edu.puj.talktome.databinding.ActivityHomeTalkerBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeTalkerActivity extends AppCompatActivity {

    private ActivityHomeTalkerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeTalkerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnUbicacion.setOnClickListener(view -> startActivity(new Intent(this, LocationsTalkerActivity.class)));

        binding.btnCita.setOnClickListener(view -> startActivity(new Intent(this, AppointmentsTalkerActivity.class)));

        binding.btnDiagnostico.setOnClickListener(view -> startActivity(new Intent(this, DiagnosesTalkerActivity.class)));

        binding.btnHistorial.setOnClickListener( view -> startActivity(new Intent( this, HistoryActivity.class)));

        binding.bottomNavigation.setSelectedItemId(R.id.page_Home);

        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.page_Notification:
                        startActivity(new Intent(getApplicationContext(),NotificacionesTalkerActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_Home:
                        return true;
                    case R.id.page_Profile:
                        startActivity(new Intent(getApplicationContext(),PerfilTalkerActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.logoutButton:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                return false;
            }
        });

        // binding.btnPerfilProfesional.setOnClickListener(view -> startActivity(new Intent(this, AvailableProfessionalsActivity.class)));

       // binding.btnPerfil.setOnClickListener(view -> startActivity(new Intent(this, PerfilTalkerActivity.class)));

       // binding.btnNotification.setOnClickListener( view -> startActivity(new Intent( this, NotificacionesTalkerActivity.class)));
    }



    /*@Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 1:
                startActivity(new Intent(this, AvailableProfessionalsActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, PerfilTalkerActivity.class));
                break;
            case 3:
                startActivity(new Intent( this, NotificacionesTalkerActivity.class));
                break;
            default:
                break;
        }
        return false;
    }*/

}