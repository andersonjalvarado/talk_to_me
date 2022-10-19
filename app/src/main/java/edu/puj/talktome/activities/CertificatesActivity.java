package edu.puj.talktome.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import android.widget.ArrayAdapter;

import org.json.JSONException;
import android.view.View;
import android.widget.AdapterView;
import java.io.IOException;
import java.util.ArrayList;

import edu.puj.talktome.databinding.ActivityAvailableProfessionalsBinding;
import edu.puj.talktome.databinding.ActivityCertificatesBinding;
import edu.puj.talktome.data.CertificatesFromJson;

public class CertificatesActivity extends BasicActivity {

    private ActivityCertificatesBinding binding;
    private final int CAMERA_PERMISSION_ID = 101;
    private final int GALLERY_PERMISSION_ID = 102;
    String cameraPerm = Manifest.permission.CAMERA;
    private CertificatesFromJson certificatesFromJson  = new CertificatesFromJson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCertificatesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.AddCertificates.setOnClickListener(view -> {
            startGallery(binding.getRoot());
        });
         /*try {
            certificatesFromJson.loadCertificatesByJson(getAssets().open(CertificatesFromJson.COUNTRIES_FILE));
            ArrayList<String> listaNombres = new ArrayList<>();
            for (int i = 0; i < certificatesFromJson.getCertificates().length(); i++) {
                String linea =certificatesFromJson.getCertificates().getJSONObject(i).get("Title").toString();
                linea += "\n";
                linea +=certificatesFromJson.getCertificates().getJSONObject(i).get("University").toString();
                linea += "\n";
                linea +=certificatesFromJson.getCertificates().getJSONObject(i).get("Date").toString();
                listaNombres.add(linea);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaNombres);
            binding.listaCertificados.setAdapter(adapter);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }*/
    }

    public void startGallery(View view){
        Intent pickGalleryImage = new Intent(Intent.ACTION_PICK);
        pickGalleryImage.setType("image/*");
        startActivityForResult(pickGalleryImage, GALLERY_PERMISSION_ID);
    }
}