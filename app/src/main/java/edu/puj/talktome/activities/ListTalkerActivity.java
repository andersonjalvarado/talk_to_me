package edu.puj.talktome.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import edu.puj.talktome.data.PatientsFromJson;
import edu.puj.talktome.databinding.ActivityListPacientesBinding;

import android.os.Bundle;

public class ListTalkerActivity extends AppCompatActivity {

    private ActivityListPacientesBinding binding;
    private PatientsFromJson patientsFromJson  = new PatientsFromJson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListPacientesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            patientsFromJson.loadPatientsByJson(getAssets().open(PatientsFromJson.COUNTRIES_FILE));
            ArrayList<String> listaNombres = new ArrayList<>();
            for (int i = 0; i < patientsFromJson.getPatients().length(); i++) {
                String linea ="Nombre: ";
                linea +=patientsFromJson.getPatients().getJSONObject(i).get("Name").toString();
                linea += "\n";
                linea +="Diagnostico: ";
                linea +=patientsFromJson.getPatients().getJSONObject(i).get("Problem").toString();
                linea += "\n";
                linea +="Modalidad: ";
                linea +=patientsFromJson.getPatients().getJSONObject(i).get("Mode").toString();
                linea += "\n";
                listaNombres.add(linea);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaNombres);
            binding.listaPacientes.setAdapter(adapter);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}