package edu.puj.talktome.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import edu.puj.talktome.R;
import edu.puj.talktome.databinding.ActivityLoginBinding;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import edu.puj.talktome.models.UserInfo;
import edu.puj.talktome.utils.AlertsHelper;

import edu.puj.talktome.models.DatabaseRoutes;

public class LoginActivity extends BasicActivity {
    public static final String TAG = LoginActivity.class.getName();
    private ActivityLoginBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference reference;
    ValueEventListener listener;

    private List<String> names = new ArrayList<>();
    private List<String> uuids = new ArrayList<>();
    ArrayAdapter adapter;

    String uid;
    private String rol;
    String auxUuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        //reference = mDatabase.getReference(DatabaseRoutes.getUser(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()));
        reference = mDatabase.getReference(DatabaseRoutes.USERS_PATH);

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren().forEach(dataSnapshot -> {
                    UserInfo tmpUser = dataSnapshot.getValue(UserInfo.class);
                    String uuid = dataSnapshot.getRef().getKey();
                    Log.e(TAG,"Uiid"+uuid);
                    if(!uuids.contains(uuid)){
                        names.add(tmpUser.getEmail());
                        uuids.add(uuid);
                    }
                        });
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: ", error.toException());
            }
        };

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, names);

        binding.btnInicioSesion.setOnClickListener(view -> doLogin());
        binding.recContrasena.setOnClickListener(view -> doPassReset());
        binding.registrateTextView.setOnClickListener(view -> startActivity(new Intent(this, RoleActivity.class)));

    }

    public static Intent createIntent(Activity activity) {
        return new Intent(activity, LoginActivity.class);
    }

    private void doLogin() {
        String email = Objects.requireNonNull(binding.correoTextField.getEditText()).getText().toString();
        String pass = Objects.requireNonNull(binding.contrasenaTextField.getEditText()).getText().toString();

        if (email.isEmpty()) {
            alertsHelper.shortSimpleSnackbar(binding.getRoot(), getString(R.string.mail_error_label));
            binding.correoTextField.setErrorEnabled(true);
            binding.correoTextField.setError(getString(R.string.mail_error_label));
            return;
        }

        if (pass.isEmpty()) {
            alertsHelper.shortSimpleSnackbar(binding.getRoot(), getString(R.string.error_pass_label));
            binding.contrasenaTextField.setErrorEnabled(true);
            binding.contrasenaTextField.setError(getString(R.string.error_pass_label));
            return;
        }

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(authResult -> {
                    //if(rol.equals("talker"))
                    //startActivity(new Intent(LoginActivity.this, HomeTalkerActivity.class));
                    //if(rol.equals("profesional"))
                        //startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
                    if(names.contains(binding.correoTextField.getEditText().getText().toString())){
                        Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                        auxUuid = String.valueOf(uuids.get(names.indexOf(binding.correoTextField.getEditText().getText().toString())));
                        //auxUuid="uoQgS7aynGtbccoUNwh78iuqeUd73";
                        intent.putExtra("uuid",auxUuid);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(e ->
                        alertsHelper.shortSimpleSnackbar(binding.getRoot(), e.getLocalizedMessage()));
    }

    private void doPassReset() {
        String email = Objects.requireNonNull(binding.correoTextField.getEditText()).getText().toString();

        if (email.isEmpty()) {
            alertsHelper.shortSimpleSnackbar(binding.getRoot(), getString(R.string.mail_error_label));
            binding.correoTextField.setErrorEnabled(true);
            binding.correoTextField.setError(getString(R.string.mail_error_label));
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnSuccessListener(authResult ->
                        alertsHelper.shortSimpleSnackbar(binding.getRoot(),"Revise su correo."))
                .addOnFailureListener(e ->
                        alertsHelper.shortSimpleSnackbar(binding.getRoot(), e.getLocalizedMessage()));
    }
    @Override
    protected void onStart() {
        super.onStart();
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        reference.removeEventListener(listener);
    }
}