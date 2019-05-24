package com.example.timer;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

class MyLocationListener implements LocationListener {

    // static Location imHere; // здесь будет всегда доступна самая последняя информация о местоположении пользователя.

    public void SetUpLocationListener(Context context) // это нужно запустить в самом начале работы программы
    {

    }

    @Override
    public void onLocationChanged(Location loc) {
        //imHere = loc;
    }
    @Override
    public void onProviderDisabled(String provider) {}
    @Override
    public void onProviderEnabled(String provider) {}
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}
