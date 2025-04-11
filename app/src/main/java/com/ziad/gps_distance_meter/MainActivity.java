package com.ziad.gps_distance_meter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class MainActivity extends AppCompatActivity {
    Button Btn_Dis, Btn_Loc;


    //private AdView adMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        //  adMain   = findViewById(R.id.ad_main);
        AdRequest adRequest = new AdRequest.Builder().build();
      //  adMain.loadAd(adRequest);

        Btn_Dis = findViewById(R.id.Btn_Dis);
        Btn_Loc = findViewById(R.id.Btn_loc);

        Intent ToDis = new Intent(this, Distance.class);
        Intent ToLoc = new Intent(this, Maps_loc.class);

        Btn_Dis.setOnClickListener( v -> {
                 startActivity(ToDis);

            }
        );


        Btn_Loc.setOnClickListener(v -> {
                startActivity(ToLoc);


        });
    }


    public void mfinish(){
        finish();
    }

    public void onDestroy() {

        super.onDestroy();

    }
}