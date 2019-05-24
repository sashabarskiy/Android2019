package com.example.timer;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView tvEnabledGPS;
    TextView tvStatusGPS;
    TextView tvLocationGPS;

    TextView tvEnabledNet;
    TextView tvStatusNet;
    TextView tvLocationNet;

    private LocationManager locationManager;
    StringBuilder sbGPS = new StringBuilder();
    StringBuilder sbNet = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // це таймер начало
        final TextView textView = findViewById(R.id.textView);
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                textView.setText("seconds remaining: " + millisUntilFinished / 1000);
            }
            public void onFinish() {
                textView.setText("done!");
            }
        }.start();
        // це таймер конец

        tvEnabledGPS = findViewById(R.id.tvEnabledGPS);
        tvStatusGPS = findViewById(R.id.tvStatusGPS);
        tvLocationGPS = findViewById(R.id.tvLocationGPS);
        tvEnabledNet = findViewById(R.id.tvEnabledNet);
        tvStatusNet = findViewById(R.id.tvStatusNet);
        tvLocationNet = findViewById(R.id.tvLocationNet);

    }

    boolean checkSelfPermission(){
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    1
            );

            if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ){

                return false;

            }
        }

        return true;
    }

    @Override
    protected void onResume(){
        super.onResume();

        if(checkSelfPermission())
            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 10, 10, locationListener);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }

    @Override
    protected void onPause(){
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if(provider.equals(LocationManager.GPS_PROVIDER)){
                tvStatusGPS.setText("Status: " + status);
            } else if (provider.equals(LocationManager.NETWORK_PROVIDER)){
                tvEnabledNet.setText("Status: " + status);
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
            checkEnabled();

            if(checkSelfPermission())
                showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();
        }
    };

    private void showLocation(Location location){
        if(location == null)
            return;
        if(location.getProvider().equals(LocationManager.GPS_PROVIDER)){
            tvLocationGPS.setText(formatLocation(location));
        } else if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)){
            tvLocationNet.setText(formatLocation(location));
        }
    }

    private String formatLocation(Location location){
        if(location == null)
            return "";
        return String.format("Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tF",
                location.getLatitude(), location.getLongitude(), new Date(location.getTime()));
    }

    private void checkEnabled(){
        tvEnabledGPS.setText("Enabled: " + locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
        tvEnabledNet.setText("Enabled: " + locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }

    public void onClickLocationSettings(View view){
        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }


}
