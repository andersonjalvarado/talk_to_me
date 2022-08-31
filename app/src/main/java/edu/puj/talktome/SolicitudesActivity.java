package edu.puj.talktome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

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
    }

    public void onClick(View view) {

        //startActivity(new Intent(this, );
    }
}