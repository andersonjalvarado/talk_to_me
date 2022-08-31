package edu.puj.talktome;

import androidx.appcompat.app.AppCompatActivity;
import edu.puj.talktome.databinding.ActivitySprofesionalBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

public class SProfesionalActivity extends AppCompatActivity {

    private ActivitySprofesionalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySprofesionalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Spinner android
        llenarSpinner();

        binding.btnInicioSesion.setOnClickListener(view -> {
            registrarse();
        });
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