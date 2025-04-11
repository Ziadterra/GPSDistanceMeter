package com.ziad.gps_distance_meter;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;


// Interface, abstract and can be implemented by other classes
// Defining a method's body that's inside an interface is done later
interface LocationTracker {
    void Run();
}

public class GpsTracker extends Service implements LocationListener {
    //creating a variable from the implemented class context
    public final Context mContext;
    // creating a variable from the interface just created
    public final LocationTracker locationTracker;

    //GPS status
    public boolean isGPSEnabled = false;

    //network status
    public boolean isNetworkEnabled = false;

    // this is true if either the GPS is enabled or network is enabled
    boolean canGetLocation = false;

    Location location; //creating a variable from the class Location
    //                   used to get the distance instead of using longtitude and latitude
    double latitude;
    double longitude;

    public static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // meters
    public static final long MIN_DISTANCE_CHANGE_FOR_UPDATES_WALK = 5; // meters


    // The minimum time between updates in milliseconds
    public static final long MIN_TIME_BW_UPDATES = 1000 * 7; //  seconds
    public static final long MIN_TIME_BW_UPDATES_WALK = 1000 * 7; //  seconds



    // Creating variable from location manager used for getting current and last location
   public LocationManager locationManager;

    //constructor to be able to access the class from other classes

    public GpsTracker(Context context, LocationTracker locTracker) {
        // set the location tracker value to whatever the accessing class wants it to be
        this.locationTracker = locTracker;
        this.mContext = context;
        getLocation();
    }

    //@RequiresApi(api = Build.VERSION_CODES.R)
    public void getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startUsingGPS() {
        try {
            if (!isGPSEnabled && !isNetworkEnabled) {
                //no location
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setMessage("Neither GPS nor Network are enabled");
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    //check the network permission
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                    }
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                }
                // if GPS Enabled get lat/long using GPS Services
                else if (isGPSEnabled) {
                    //check the network permission
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                    }

                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    Log.d("GPS Enabled", "GPS Enabled");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Stop using GPS listener
     * Calling this function will stop using GPS
     */
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GpsTracker.this);
        }
    }

    @Override
    public void onLocationChanged(Location loc) {
        location = loc;
        latitude = loc.getLatitude();
        longitude = loc.getLongitude();

        Log.d("location", String.format("lat: %s, long: %s", loc.getLatitude(), loc.getLongitude()));
        // run event
        this.locationTracker.Run();
    }

    /*@Override
    public void onProviderEnabled(String var1) {

    }/*

   /* @Override
    public void onProviderDisabled(String var1) {

    }*/


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
