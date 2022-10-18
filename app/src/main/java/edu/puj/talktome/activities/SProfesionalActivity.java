package edu.puj.talktome.activities;

import androidx.appcompat.app.AppCompatActivity;

import edu.puj.talktome.R;
import edu.puj.talktome.databinding.ActivitySprofesionalBinding;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;

import java.util.Calendar;

public class SProfesionalActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySprofesionalBinding binding;
    private int dia, mes, ano;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySprofesionalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Calendario
        binding.creaTextView.setOnClickListener(this);
        //Spinner android
        llenarSpinner();

        binding.btnInicioSesion.setOnClickListener(view -> {
            registrarse();
        });
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
    private void registrarse(){
        Intent intent = new Intent(this,RegistroActivity.class);
        intent.putExtra("nombre",binding.nombreEditTextField.getText().toString());
        intent.putExtra("valor",getIntent().getStringExtra("valor"));
        startActivity(intent);
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