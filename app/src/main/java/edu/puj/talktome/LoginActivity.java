package edu.puj.talktome;

import androidx.appcompat.app.AppCompatActivity;
import edu.puj.talktome.databinding.ActivityLoginBinding;

import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.os.Bundle;

import java.util.Objects;
import edu.puj.talktome.utils.AlertsHelper;
public class LoginActivity extends AppCompatActivity {
    public static final String TAG = LoginActivity.class.getName();
    private ActivityLoginBinding binding;
    private AlertsHelper alertsHelper;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();


        //binding.btnInicioSesion.setOnClickListener(view -> startActivity(new Intent(this, HomeTalkerActivity.class)));
        binding.btnInicioSesion.setOnClickListener(view -> doLogin());
        binding.registrateTextView.setOnClickListener(view -> startActivity(new Intent(this, RoleActivity.class)));
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
                    startActivity(new Intent(this, HomeTalkerActivity.class));
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
}