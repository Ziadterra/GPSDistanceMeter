package com.ziad.gps_distance_meter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Maps_loc extends AppCompatActivity implements OnMapReadyCallback {
    private ProgressBar spinner;
    private Layer layer;
    SupportMapFragment map;

    GpsTracker gpsTracker;
    Button btn_back_map;

    final float zoom = 17.5f;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_loc);

        btn_back_map = findViewById(R.id.btn_back_map);

        spinner = findViewById(R.id.spinner);
        layer = findViewById(R.id.layer);

        OnMapReadyCallback parent = this;

        map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert map != null;

        gpsTracker = new GpsTracker(Maps_loc.this, () -> runOnUiThread(() -> {
            layer.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            map.getMapAsync(parent);
        }));

        gpsTracker.startUsingGPS();

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        Intent ToMain = new Intent(this, MainActivity.class);

        btn_back_map.setOnClickListener(v ->  {
                                                startActivity(ToMain);
                                                 lfinish();
                                            }

                                            );
    }//end of oncreate

public void lfinish(){
        finish();

}




    @SuppressLint("DefaultLocale")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if (gpsTracker.longitude != 0.0 && gpsTracker.latitude != 0.0) {


            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsTracker.latitude, gpsTracker.longitude), zoom));

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(gpsTracker.latitude, gpsTracker.longitude));
            googleMap.addMarker(markerOptions);

        }
    }


}


