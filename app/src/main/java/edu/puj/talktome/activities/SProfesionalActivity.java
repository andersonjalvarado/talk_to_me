package edu.puj.talktome.activities;

import edu.puj.talktome.R;
import edu.puj.talktome.databinding.ActivitySprofesionalBinding;
import edu.puj.talktome.models.DatabaseRoutes;
import edu.puj.talktome.models.ProfessionalInfo;
import edu.puj.talktome.utils.AlertUtils;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.github.javafaker.Faker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;

public class SProfesionalActivity extends BasicActivity implements View.OnClickListener {

    private ActivitySprofesionalBinding binding;
    private int dia, mes, ano;

    private AlertUtils alertUtils;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySprofesionalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Calendario
        binding.creaTextView.setOnClickListener(this);

        //Spinner android
        llenarSpinner();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        binding.btnInicioSesion.setOnClickListener(view -> doSignup());
        /*binding.btnInicioSesion.setOnClickListener(view -> {
            registrarse();
        });*/
    }
    @Override
    public void onClick(View view) {
        final Calendar calendar = Calendar.getInstance();
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        ano = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                binding.textNacimiento.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
            }
        },dia,mes,ano);
        datePickerDialog.show();
    }

    private void doSignup() {
        String email = Objects.requireNonNull(binding.correoTextField.getEditText()).getText().toString();
        String pass = Objects.requireNonNull(binding.contrasenaTextField.getEditText()).getText().toString();
        String pass2 = Objects.requireNonNull(binding.confirmaTextField.getEditText()).getText().toString();
        String rol = "profesional";


        if (email.isEmpty()) {
            alertUtils.shortSimpleSnackbar(binding.getRoot(), getString(R.string.mail_error_label));
            binding.correoTextField.setErrorEnabled(true);
            binding.correoTextField.setError(getString(R.string.mail_error_label));
            return;
        }else{
            if(!email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
                alertUtils.shortSimpleSnackbar(binding.getRoot(),"Digita un correo válido");
                return;
            }
        }

        if (pass.isEmpty()) {
            alertUtils.shortSimpleSnackbar(binding.getRoot(), getString(R.string.error_pass_label));
            binding.contrasenaTextField.setErrorEnabled(true);
            binding.contrasenaTextField.setError(getString(R.string.error_pass_label));
            return;
        }else{
            if(pass.length() < 6){
                alertUtils.shortSimpleSnackbar(binding.getRoot(),"La contraseña de ser mayor a 6 caracteres");
                return;
            }
        }

        if (pass.equals(pass2)) {


            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnSuccessListener(authResult -> {
                        DatabaseReference reference = mDatabase.getReference(DatabaseRoutes.getUser(authResult.getUser().getUid()));
                        ProfessionalInfo tmpUser = new ProfessionalInfo(
                                Objects.requireNonNull(binding.nombreTextField.getEditText()).getText().toString().isEmpty() ? Faker.instance().funnyName().name() : binding.nombreTextField.getEditText().getText().toString(),
                                Objects.requireNonNull(binding.tipoIdTextField.getEditText()).getText().toString().isEmpty() ? Faker.instance().funnyName().name() : binding.tipoIdTextField.getEditText().getText().toString(),
                                Long.parseLong(Objects.requireNonNull(binding.idTextField.getEditText()).getText().toString().isEmpty() ? Faker.instance().funnyName().name() : binding.idTextField.getEditText().getText().toString()),
                                Long.parseLong(Objects.requireNonNull(binding.celTextField.getEditText()).getText().toString().isEmpty() ? Faker.instance().phoneNumber().cellPhone().replace("-", "") : binding.celTextField.getEditText().getText().toString()),
                                Objects.requireNonNull(binding.correoTextField.getEditText()).getText().toString().isEmpty() ? Faker.instance().funnyName().name() : binding.correoTextField.getEditText().getText().toString(),

                                binding.correoTextField.getEditText().getText().toString(), rol,

                                Double.parseDouble(Objects.requireNonNull(binding.latitud.getEditText()).getText().toString().isEmpty() ? Faker.instance().numerify("35.232") : binding.latitud.getEditText().getText().toString()),
                                Double.parseDouble(Objects.requireNonNull(binding.longitud.getEditText()).getText().toString().isEmpty() ? Faker.instance().numerify("35.232") : binding.longitud.getEditText().getText().toString()));


                                reference.setValue(tmpUser).addOnSuccessListener(unused ->
                                {
                                    Intent intent = new Intent(SProfesionalActivity.this, HomeProfesionalActivity.class);

                                    startActivity(intent);
                                })
                                .addOnFailureListener(e ->
                                        alertUtils.shortSimpleSnackbar(binding.getRoot(), e.getLocalizedMessage()));
                    })
                    .addOnFailureListener(e ->
                            alertUtils.shortSimpleSnackbar(binding.getRoot(), e.getLocalizedMessage()));
        } else {
            alertUtils.shortSimpleSnackbar(binding.getRoot(), getString(R.string.errorContrasena));
        }
    }

    private void llenarSpinner(){
        String [] campos = new String[]{getString(R.string.CC),getString(R.string.TI),getString(R.string.CE),getString(R.string.RC)};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.contenido_id,
                campos
        );
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.tipoId_exposed);
        autoCompleteTextView.setAdapter(adapter);
    }


}