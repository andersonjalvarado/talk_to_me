package edu.puj.talktome.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
    FirebaseDatabase mDatabase;
    String rol;
    SensorManager sensorManager;
    Sensor lightSensor, acelerometroSensor;
    SensorEventListener lightSensorEventListener, acelerometroSensorEventListener;
    GoogleMap.OnMapLongClickListener longClickListener;
    public static final String TAG = RegistroActivity.class.getName();

    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    ValueEventListener listener;
    private List<Pair> ubicaciones = new ArrayList<>();



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



            googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(@NonNull LatLng latLng) {
                    findPlaceByLocation(latLng);
                }
            });
            //Setup the route line
            userRoute = googleMap.addPolyline(new PolylineOptions()
                    .color(R.color.light_blue_400)
                    .width(15.0f)
                    .geodesic(true)
                    .zIndex(0.5f));
            //Setup the rest of the markers based in a json file
            loadGeoInfo();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ((App) requireActivity().getApplicationContext()).getAppComponent().inject(this);
        binding = ActivityFragmentMapBinding.inflate(inflater); //---------------
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

        binding.materialButton.setOnClickListener(view1 -> findPlaces(binding.textInputLayout.getEditText().getText().toString()));
        binding.textInputLayout.getEditText().setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        binding.textInputLayout.getEditText().setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                findPlaces(binding.textInputLayout.getEditText().getText().toString());
                return true;
            }
            return false;
        });
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

//    private void loadGeoInfo() {
//        //String uuid = getIntent().getStringExtra("uuid");
//        mDatabase = FirebaseDatabase.getInstance();
//
//        DatabaseReference reference = mDatabase.getReference(DatabaseRoutes.USERS_PATH);
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                snapshot.getChildren().forEach(dataSnapshot -> {
//                    ProfessionalInfo tmpUser = dataSnapshot.getValue(ProfessionalInfo.class);
//                    String uuid = dataSnapshot.getRef().getKey();
//                    Log.e(TAG,"Uiid"+uuid);
//                    //if(!uuids.contains(uuid)){
//                        ubicaciones.add(new Pair(tmpUser.getLatitud(),tmpUser.getLongitud()));
//                      //  uuids.add(uuid);
//                  //  }
//                });
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e(TAG, "onCancelled: ", error.toException());
//            }
//        });
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            for (int i=0; i<ubicaciones.size(); i++) {
//                MarkerOptions newMarker = new MarkerOptions();
//                newMarker.position(new LatLng(Double.valueOf(ubicaciones.get(i).first.toString()) , Double.valueOf(ubicaciones.get(i).second.toString())));
//                newMarker.icon(BitmapUtils.getBitmapDescriptor(getContext(), R.drawable.ic_baseline_person_pin_circle_24));
//                googleMap.addMarker(newMarker);
//            }
//        }
//    }

    private void loadGeoInfo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            geoInfoFromJsonService.getGeoInfoList().forEach(geoInfo -> {
                MarkerOptions newMarker = new MarkerOptions();
                newMarker.position(new LatLng(geoInfo.getLat(), geoInfo.getLng()));
                newMarker.title(geoInfo.getTitle());
                newMarker.snippet(geoInfo.getContent());
                if (geoInfo.getImageBase64() != null) {
                    byte[] pinImage = Base64.decode(geoInfo.getImageBase64(), Base64.DEFAULT);
                    Bitmap decodedPin = BitmapFactory.decodeByteArray(pinImage, 0, pinImage.length);
                    Bitmap smallPin = Bitmap.createScaledBitmap(decodedPin, 200, 200, false);
                    newMarker.icon(BitmapDescriptorFactory.fromBitmap(smallPin));
                }
                googleMap.addMarker(newMarker);
            });
        }
    }

    public void findPlaces(String search) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                final LatLng[] newPosition = new LatLng[1];
                //List<Address> results = geocoderService.finPlacesByNameInRadious(search, userPosition.getPosition());
                List<Address> results = geocoderService.findPlacesByName(search);
                Log.d("TAG", "findPlaces: results = " + results.size());
                results.forEach(address -> googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapUtils.getBitmapDescriptor(getContext(), R.drawable.ic_baseline_person_pin_circle_24))
                        .position(newPosition[0] = new LatLng(address.getLatitude(), address.getLongitude()))
                        .title(address.getAddressLine(0))
                        .snippet(address.getAddressLine(0) != null ? address.getAddressLine(0) : ""))
                );
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newPosition[0],16));
                String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+userPosition.getPosition().latitude +","+ userPosition.getPosition().longitude+"&destination="+ newPosition[0].latitude+","+newPosition[0].longitude+"&key=AIzaSyAoQL0aSH8DfaDg7GyiwGEaJi6EcARlGXI";
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
                    try {
                        JSONObject json = new JSONObject(response);
                        trazarRuta(json);
                        Log.i("Trazar ruta", response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {

                });
                queue.add(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void trazarRuta(JSONObject json) {
        JSONArray routes;
        JSONArray legs;
        JSONArray steps;
        try{
            routes = json.getJSONArray("routes");
            for (int i = 0; i<routes.length();i++){
                legs = ((JSONObject)(routes.get(i))).getJSONArray("legs");
                for(int j = 0; j< legs.length(); j++){
                    steps = ((JSONObject)legs.get(j)).getJSONArray("steps");
                    for(int k = 0; k< steps.length(); k++){
                        String polyline = ""+((JSONObject)((JSONObject)steps.get(k)).get("polyline")).get("points");
                        Log.i("Final", polyline);
                        List<LatLng> list = PolyUtil.decode(polyline);
                        googleMap.addPolyline(new PolylineOptions().addAll(list).color(Color.MAGENTA).width(5));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void findPlaceByLocation(LatLng latLng){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                List<Address> results = geocoderService.findPlacesByPosition(latLng);
                Log.d("TAG", "findPlaces: results = " + results.size());
                results.forEach(address -> googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(address.getLatitude(), address.getLongitude()))
                        .title(address.getFeatureName())
                        .snippet(address.getAddressLine(0) != null ? address.getAddressLine(0) : "")));
                if (results.size() > 0) {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng( new LatLng(results.get(0).getLatitude(), results.get(0).getLongitude())));
                    int dist = DistanceUtils.calculateDistanceInKilometer(userPosition.getPosition().latitude, userPosition.getPosition().longitude,
                            results.get(0).getLatitude(), results.get(0).getLongitude());
                    String mensaje;
                    mensaje = String.format("La distancia que hay entre su posición\n" +
                            "actual y el marcador creado es de: %skm", dist);
                    AlertUtils.longToast(getContext(), mensaje);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}