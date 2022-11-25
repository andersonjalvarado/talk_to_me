package edu.puj.talktome.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.PolyUtil;


import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import edu.puj.talktome.App;
import edu.puj.talktome.R;
import edu.puj.talktome.databinding.ActivityFragmentMapBinding;
import edu.puj.talktome.models.DatabaseRoutes;
import edu.puj.talktome.models.ProfessionalInfo;
import edu.puj.talktome.models.UserInfo;
import edu.puj.talktome.services.GeoInfoFromJsonService;
import edu.puj.talktome.services.GeocoderService;
import edu.puj.talktome.utils.AlertUtils;
import edu.puj.talktome.utils.BitmapUtils;
import edu.puj.talktome.utils.DistanceUtils;

public class FragmentMap extends Fragment {
    @Inject
    GeoInfoFromJsonService geoInfoFromJsonService;

    @Inject
    GeocoderService geocoderService;

    ActivityFragmentMapBinding binding;

    AlertUtils alertUtils;
    //Map interaction variables
    GoogleMap googleMap;
    static final int INITIAL_ZOOM_LEVEL = 18;
    private final LatLng UNIVERSIDAD_JAVERIANA = new LatLng(4.6285, -74.0646);
    Marker userPosition;
    Polyline userRoute;

    //Light sensor variables
    final static float LIGHT_LIMIT = 5000.0f;
    final static int ACELEROMETROX = 1;
    int whip = 0;
    private FirebaseDatabase mDatabase;
    private DatabaseReference reference;
    ValueEventListener listener;
    String rol;
    SensorManager sensorManager;
    Sensor lightSensor, acelerometroSensor;
    SensorEventListener lightSensorEventListener, acelerometroSensorEventListener;
    GoogleMap.OnMapLongClickListener longClickListener;
    public static final String TAG = RegistroActivity.class.getName();
   // private List<LatLng> ubicaciones = new ArrayList<>();



    private final OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap map) {
            //Setup the map
            googleMap = map;
            googleMap.moveCamera(CameraUpdateFactory.zoomTo(INITIAL_ZOOM_LEVEL));
            googleMap.getUiSettings().setAllGesturesEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setZoomGesturesEnabled(true);
            googleMap.getUiSettings().setCompassEnabled(true);
            // Change map style
            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_day_style));
            //Setup the user marker with a default position
            userPosition = googleMap.addMarker(new MarkerOptions()
                    .position(UNIVERSIDAD_JAVERIANA)
                    .icon(BitmapUtils.getBitmapDescriptor(getContext(), R.drawable.ic_baseline_person_pin_circle_24))
                    .anchor(0.5f, 0.5f)
                    .zIndex(1.0f));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(UNIVERSIDAD_JAVERIANA));


            //Setup the route line
            userRoute = googleMap.addPolyline(new PolylineOptions()
                    .color(R.color.light_blue_400)
                    .width(15.0f)
                    .geodesic(true)
                    .zIndex(0.5f));
            //Setup the rest of the markers based in a json file
            loadGeoInfo();
            googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(@NonNull LatLng latLng) {
                    PolylineOptions polylineOptions = new PolylineOptions()
                            .width(15.0f)
                            .color(R.color.light_blue_400)
                            .geodesic(true)
                            .zIndex(0.5f)
                            .add(new LatLng(userPosition.getPosition().latitude,userPosition.getPosition().longitude))
                            .add(new LatLng(latLng.latitude, latLng.longitude));
                    userRoute = googleMap.addPolyline(polylineOptions);
                    double dist = distance(userPosition.getPosition().latitude, userPosition.getPosition().longitude, latLng.latitude, latLng.longitude);
                    Toast.makeText(getContext(), "Distancia: " + dist + " km", Toast.LENGTH_LONG).show();
                }
            });
            binding.materialButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (userRoute !=null) userRoute.remove();
                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ((App) requireActivity().getApplicationContext()).getAppComponent().inject(this);
        binding = ActivityFragmentMapBinding.inflate(inflater); //--------------
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        lightSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (googleMap != null) {
                    if (sensorEvent.values[0] < LIGHT_LIMIT)
                        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_night_style));
                    else
                        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_day_style));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        acelerometroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if(acelerometroSensor==null)
            alertUtils.shortSimpleSnackbar(binding.getRoot(), getString(R.string.notsensor));

        acelerometroSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (googleMap != null) {
                    if (sensorEvent.values[0] < -5 && whip == 0) {
                        whip++;
                        alertUtils.shortSimpleSnackbar(binding.getRoot(), getActivity().getString(R.string.rDerecha));
                        //googleMap.setMapType(googleMap.MAP_TYPE_TERRAIN);
                    } else {
                        if (sensorEvent.values[0] > 5 && whip == 1) {
                            whip++;
                            alertUtils.shortSimpleSnackbar(binding.getRoot(), getActivity().getString(R.string.rIzquierda));
                            //googleMap.setMapType(googleMap.MAP_TYPE_SATELLITE);
                        }
                    }
                    if (whip == 2) {
                        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_day_style));
                        whip = 0;
                        //alertUtils.shortSimpleSnackbar(binding.getRoot(), getString(R.string.enssayo3));
                    }
                }
                /*if (googleMap != null) {

                    if (sensorEvent.values[0] < ACELEROMETROX){
                        alertUtils.shortSimpleSnackbar(binding.getRoot(), getString(R.string.ensato1));
                        //googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_day_style));
                    } else if(sensorEvent.values[0] > ACELEROMETROX ) {
                        alertUtils.shortSimpleSnackbar(binding.getRoot(), getString(R.string.ensato2));
                        //googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_night_style));
                    }
                }*/
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        sensorManager.registerListener(acelerometroSensorEventListener,acelerometroSensor,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onStart() {
        super.onStart();
        sensorManager.registerListener(lightSensorEventListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onStop() {
        super.onStop();
        List<LatLng> points = userRoute.getPoints();
        points.clear();
        userRoute.setPoints(points);
        sensorManager.unregisterListener(lightSensorEventListener);
    }

    public void updateUserPositionOnMap(@NotNull LocationResult locationResult) {
        userPosition.setPosition(new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude()));
        List<LatLng> points = userRoute.getPoints();
        points.add(userPosition.getPosition());
        userRoute.setPoints(points);
        if (binding.isCameraFixedToUser.isChecked()) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(userPosition.getPosition()));
//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userPosition.getPosition(), INITIAL_ZOOM_LEVEL));
        }
    }

    private void loadGeoInfo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            geoInfoFromJsonService.getGeoInfoList().forEach(geoInfo -> {
                MarkerOptions newMarker = new MarkerOptions();
                newMarker.position(new LatLng(geoInfo.getLat(), geoInfo.getLng()));
                newMarker.title(geoInfo.getTitle());
                newMarker.snippet(geoInfo.getContent());
                newMarker.icon(BitmapUtils.getBitmapDescriptor(getContext(), R.drawable.ic_baseline_medical_services_24));

                googleMap.addMarker(newMarker);
            });
        }
    }
    private double distance(double myLat, double myLong, double otherLat, double otherLong){
        double latDistance = Math.toRadians(myLat - otherLat);
        double longDistance = Math.toRadians(myLong - otherLong);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(myLat)) *
                Math.cos(Math.toRadians(otherLat)) * Math.sin(longDistance / 2) * Math.sin(longDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double res = 6371.01 * c;

        return Math.round(res * 100.0) / 100.0;
    }
}