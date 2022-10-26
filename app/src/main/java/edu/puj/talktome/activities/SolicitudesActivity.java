package edu.puj.talktome.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import edu.puj.talktome.R;
import edu.puj.talktome.data.RequestFromJson;
import edu.puj.talktome.databinding.ActivitySolicitudesBinding;

public class SolicitudesActivity extends AppCompatActivity {

    private ActivitySolicitudesBinding binding;
    private RequestFromJson requestFromJson = new RequestFromJson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySolicitudesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /*try {
            requestFromJson .loadRequestByJson(getAssets().open(RequestFromJson .COUNTRIES_FILE));
            ArrayList<String> listaNombres = new ArrayList<>();
            for (int i = 0; i < requestFromJson.getRequest().length(); i++) {
                listaNombres.add(requestFromJson .getRequest().getJSONObject(i).get("NamePatient").toString());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaNombres);
            binding.listaSolicitudes.setAdapter(adapter);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }*/
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


    public void onClick (View view){
        startActivity(new Intent(this, ScheduleCitaActivity.class));
    }
}