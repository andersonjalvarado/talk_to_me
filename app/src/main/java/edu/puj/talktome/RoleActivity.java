package edu.puj.talktome;

import androidx.appcompat.app.AppCompatActivity;
import edu.puj.talktome.databinding.ActivityRoleBinding;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class RoleActivity extends AppCompatActivity {

    private ActivityRoleBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRegistrarse.setOnClickListener(view -> {
            validar();
        });
    }
    //Metodo para validar la opcion seleccionada
    private void validar(){
        if (binding.radioPaciente.isChecked())
        {
            Intent intent = new Intent(this, STalkerActivity.class);
            intent.putExtra("valor","1");
            startActivity(intent);
        }
        else if (binding.radioProfesional.isChecked())
        {
            Intent intent = new Intent(this, SProfesionalActivity.class);
            intent.putExtra("valor","0");
            startActivity(intent);
        }
        else
            Toast.makeText(getBaseContext(), "Seleccione una opción", Toast.LENGTH_LONG).show();
    }
}