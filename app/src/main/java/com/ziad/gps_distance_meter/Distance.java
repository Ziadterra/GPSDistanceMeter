package com.ziad.gps_distance_meter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Distance extends AppCompatActivity {
    GpsTracker gpsTracker;
    Button btn_back, btn_start, btn_stop;
    TextView txt_value,learn_more;
    public Switch speed;
   // private AdView adMain;

    public boolean state = false;

    double dist = 0;
    Location last_loc = null;

    enum DistanceUnit {
        Kilometers,
        NauticalMiles
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);
        btn_back = findViewById(R.id.btn_back);
        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);
        txt_value = findViewById(R.id.txt_value);
        //adMain = findViewById(R.id.ad_distance);
        speed = findViewById(R.id.speed);



        gpsTracker = new GpsTracker(Distance.this, new LocationTracker() {
            @Override
            public void Run() {
                runOnUiThread(() -> {
                    if (last_loc != null) {
                        // calc dist
                        double local_dist = 0;

                        try {
                            local_dist = distance(DistanceUnit.Kilometers, last_loc, gpsTracker.location);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        dist += local_dist;

                        txt_value.setText(String.format(getString(R.string.distance_format), dist));
                    }
                    last_loc = gpsTracker.location;
                });
            }
        });

        Intent ToMain = new Intent(this, MainActivity.class);



        btn_start.setOnClickListener(v -> {
            gpsTracker.startUsingGPS();

            dist = 0;
            txt_value.setText(String.format(getString(R.string.distance_format), dist));

            disableButton(btn_start);
            enableButton(btn_stop, Color.RED);
        });

        btn_stop.setOnClickListener(v -> {
            gpsTracker.stopUsingGPS();

            disableButton(btn_stop);
            enableButton(btn_start, Color.GREEN);

            state = false;





        });
        // This area is for ads






        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


     //   AdRequest adRequest = new AdRequest.Builder().build();
     //   adMain.loadAd(adRequest);

        //this area is for ads
        btn_back.setOnClickListener(v -> {
            startActivity(ToMain);
            finish();

        });




 if (speed.isChecked()) {
     try {
         if (!gpsTracker.isGPSEnabled && !gpsTracker.isNetworkEnabled) {
             //no location
             AlertDialog.Builder alertDialog = new AlertDialog.Builder(gpsTracker.mContext);
             alertDialog.setMessage("neither GPS nor Network are enabled");
         } else {
             gpsTracker.canGetLocation = true;
             // First get location from Network Provider
             if (gpsTracker.isNetworkEnabled) {
                 //check the network permission
                 if (ActivityCompat.checkSelfPermission(gpsTracker.mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(gpsTracker.mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                     ActivityCompat.requestPermissions((Activity) gpsTracker.mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                 }
                 gpsTracker.locationManager.requestLocationUpdates(
                         LocationManager.NETWORK_PROVIDER,
                         gpsTracker.MIN_TIME_BW_UPDATES,
                         gpsTracker.MIN_DISTANCE_CHANGE_FOR_UPDATES, gpsTracker);
                 Log.d("Network", "Network");
             }
             // if GPS Enabled get lat/long using GPS Services
             else if (gpsTracker.isGPSEnabled) {
                 //check the network permission
                 if (ActivityCompat.checkSelfPermission(gpsTracker.mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(gpsTracker.mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                     ActivityCompat.requestPermissions((Activity) gpsTracker.mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                 }

                 gpsTracker.locationManager.requestLocationUpdates(
                         LocationManager.GPS_PROVIDER,
                         gpsTracker.MIN_TIME_BW_UPDATES,
                         gpsTracker.MIN_DISTANCE_CHANGE_FOR_UPDATES,gpsTracker);

                 Log.d("GPS Enabled", "GPS Enabled");
             }
         }

     } catch (Exception e) {
         e.printStackTrace();
     }
 }
 else {
     {
         try {
             if (!gpsTracker.isGPSEnabled && !gpsTracker.isNetworkEnabled) {
                 //no location
                 AlertDialog.Builder alertDialog = new AlertDialog.Builder(gpsTracker.mContext);
                 alertDialog.setMessage("neither GPS nor Network are enabled");
             } else {
                 gpsTracker.canGetLocation = true;
                 // First get location from Network Provider
                 if (gpsTracker.isNetworkEnabled) {
                     //check the network permission
                     if (ActivityCompat.checkSelfPermission(gpsTracker.mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(gpsTracker.mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                         ActivityCompat.requestPermissions((Activity) gpsTracker.mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                     }
                     gpsTracker.locationManager.requestLocationUpdates(
                             LocationManager.NETWORK_PROVIDER,
                             gpsTracker.MIN_TIME_BW_UPDATES_WALK,
                             gpsTracker.MIN_DISTANCE_CHANGE_FOR_UPDATES_WALK, gpsTracker);
                     Log.d("Network", "Network");
                 }
                 // if GPS Enabled get lat/long using GPS Services
                 else if (gpsTracker.isGPSEnabled) {
                     //check the network permission
                     if (ActivityCompat.checkSelfPermission(gpsTracker.mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(gpsTracker.mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                         ActivityCompat.requestPermissions((Activity) gpsTracker.mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                     }

                     gpsTracker.locationManager.requestLocationUpdates(
                             LocationManager.GPS_PROVIDER,
                             gpsTracker.MIN_TIME_BW_UPDATES_WALK,
                             gpsTracker.MIN_DISTANCE_CHANGE_FOR_UPDATES_WALK,gpsTracker);

                     Log.d("GPS Enabled", "GPS Enabled");
                 }
             }

         } catch (Exception e) {
             e.printStackTrace();
         }
     }


 }


 }//end of onCreate


    private void enableButton(Button btn, int color) {
        btn.setTextColor(Color.BLACK);
        btn.setBackgroundColor(color);
        btn.setEnabled(true);
    }

    private void disableButton(Button btn) {
        btn.setTextColor(Color.GRAY);
        btn.setBackgroundColor(Color.LTGRAY);
        btn.setEnabled(false);
    }


    private double distance(DistanceUnit unit, Location loc1, Location loc2) throws InterruptedException {
        double lat1 = loc1.getLatitude();
        double lon1 = loc1.getLongitude();

        double lat2 = loc2.getLatitude();
        double lon2 = loc2.getLongitude();

        // Make sure loc1 != loc2
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == DistanceUnit.Kilometers) {
            dist = dist * 1.609344;
        } else if (unit == DistanceUnit.NauticalMiles) {
            dist = dist * 0.8684;
        }
        return (dist);
    }





    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }


    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }






}
