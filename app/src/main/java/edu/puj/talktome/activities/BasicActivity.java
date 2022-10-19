package edu.puj.talktome.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import edu.puj.talktome.App;
import edu.puj.talktome.services.LocationService;
import edu.puj.talktome.utils.AlertsHelper;
import edu.puj.talktome.utils.PermissionHelper;

import javax.inject.Inject;

public class BasicActivity extends AppCompatActivity {
    @Inject
    AlertsHelper alertsHelper;

    @Inject
    PermissionHelper permissionHelper;

    @Inject
    LocationService locationService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ((App) getApplicationContext()).getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
    }
}
