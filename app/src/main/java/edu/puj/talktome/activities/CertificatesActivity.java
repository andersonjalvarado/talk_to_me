package edu.puj.talktome.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import java.util.ArrayList;

import edu.puj.talktome.R;
import edu.puj.talktome.adapters.ImageAdapter;
import edu.puj.talktome.databinding.ActivityCertificatesBinding;

import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class CertificatesActivity extends AppCompatActivity {
    private final int GALLERY_PERMISSION_ID = 102;

    private ActivityCertificatesBinding binding;
    ArrayList<String> imagelist;
    ImageAdapter adapter;
    FirebaseDatabase mDatabase;
    FirebaseStorage mStorage;
    DatabaseReference mReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCertificatesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mDatabase = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance();
        imagelist=new ArrayList<>();

        adapter=new ImageAdapter(imagelist,this);
        binding.listaCertificados.setLayoutManager(new LinearLayoutManager(null));
        binding.progress.setVisibility(View.VISIBLE);

        StorageReference listRef = FirebaseStorage.getInstance().getReference().child("certificados");
        listRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for(StorageReference file:listResult.getItems()){
                    file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imagelist.add(uri.toString());
                            Log.e("Itemvalue",uri.toString());
                        }
                    }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            binding.listaCertificados.setAdapter(adapter);
                            binding.progress.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
        binding.BotonAddCertificates.setOnClickListener(view -> startGallery(binding.getRoot()));

        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.page_Notification:
                        startActivity(new Intent(getApplicationContext(),NotificacionesProfesionalActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_Home:
                        startActivity(new Intent(getApplicationContext(),HomeProfesionalActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_Profile:
                        startActivity(new Intent(getApplicationContext(),PerfilProfesionalActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.logoutButton:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                return false;
            }
        });
    }
    public void startGallery(View view){
        Intent pickGalleryImage = new Intent(Intent.ACTION_PICK);
        pickGalleryImage.setType("image/*");
        startActivityForResult(pickGalleryImage, GALLERY_PERMISSION_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();

            StorageReference filePath = mStorage.getReference().child("certificados").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(CertificatesActivity.this,"Se ha cargado la foto.",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}