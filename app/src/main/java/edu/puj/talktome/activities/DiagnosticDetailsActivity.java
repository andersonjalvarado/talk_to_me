package edu.puj.talktome.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import edu.puj.talktome.R;
import edu.puj.talktome.databinding.ActivityDiagnosticDetailsBinding;

public class DiagnosticDetailsActivity extends AppCompatActivity {
    private ActivityDiagnosticDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDiagnosticDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


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
                        startActivity(new Intent(getApplicationContext(),HomeTalkerActivity.class));
                        overridePendingTransition(0,0);
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
    }
}