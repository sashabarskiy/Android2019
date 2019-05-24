package com.example.timer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GeoActivity extends AppCompatActivity {

    TextView textView2;
    Button button;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo);
        // MyLocationListener.SetUpLocationListener(this);


        textView2 = findViewById(R.id.textView2);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    locationManager = (LocationManager)
                            GeoActivity.this.getSystemService(Context.LOCATION_SERVICE);

                    LocationListener locationListener = new MyLocationListener();

                    // Here, thisActivity is the current activity
                    if (ContextCompat.checkSelfPermission(GeoActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                        textView2.setText(textView2.getText() + " 1");

                        // Permission is not granted
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(GeoActivity.this,
                                Manifest.permission.ACCESS_FINE_LOCATION)) {

                            textView2.setText(textView2.getText() + " 2");

                            // Show an explanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.
                        } else {

                            textView2.setText(textView2.getText() + " 3");

                            // No explanation needed; request the permission
                            ActivityCompat.requestPermissions(GeoActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    1);

                            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    } else {

                        textView2.setText(textView2.getText() + " 4");

                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                5000,
                                10,
                                locationListener); // здесь можно указать другие более подходящие вам параметры

                        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        if(location == null)
                            textView2.setText(textView2.getText() + " null");
                        else
                            textView2.setText(textView2.getText() + " " + location.toString());

                    }
                } catch (Exception ex){
                    textView2.setText(textView2.getText() + " " + ex.getMessage());
                }

            }
        });


    }

}
