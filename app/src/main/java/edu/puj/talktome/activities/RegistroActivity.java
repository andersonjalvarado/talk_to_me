package edu.puj.talktome.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import edu.puj.talktome.R;
import edu.puj.talktome.databinding.ActivityRegistroBinding;
import edu.puj.talktome.models.DatabaseRoutes;
import edu.puj.talktome.models.UserInfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistroActivity extends AppCompatActivity {
    public static final String TAG = RegistroActivity.class.getName();

    private ActivityRegistroBinding binding;

    FirebaseDatabase mDatabase;
    String rol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Cambiar nombre

        String uuid = getIntent().getStringExtra("uuid");
        String nombreU = getIntent().getStringExtra("nombreU");
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = mDatabase.getReference(DatabaseRoutes.getUser(uuid));
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserInfo tmpUser = snapshot.getValue(UserInfo.class);
                binding.textName.setText(getIntent().getStringExtra(tmpUser.getName()));
                rol = tmpUser.getRole();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: ", error.toException());
            }
        });

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
                if (rol.equals("talker")){
                    Intent intentT = new Intent(RegistroActivity.this, HomeTalkerActivity.class);
                    intentT.putExtra("nombreU",nombreU);
                    startActivity(intentT);
                }
                else if(rol.equals("profesional")){
                    Intent intentT = new Intent(RegistroActivity.this, HomeProfesionalActivity.class);
                    intentT.putExtra("nombreU",nombreU);
                    startActivity(intentT);
                }
                finish();
            }
        },4000);
    }
}