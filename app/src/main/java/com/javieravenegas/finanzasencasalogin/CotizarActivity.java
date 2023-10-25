package com.javieravenegas.finanzasencasalogin;

import androidx.appcompat.app.AppCompatActivity;

//Nuevos
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.annotation.NonNull;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Looper;
import org.osmdroid.api.IGeoPoint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
//Fin nuevos

import android.os.Bundle;
import org.osmdroid.views.MapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.config.Configuration;

public class CotizarActivity extends AppCompatActivity {

    private MapView map;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotizar);
        Configuration.getInstance().load(getApplicationContext(), getPreferences(MODE_PRIVATE));
        MapView map = findViewById(R.id.mapview);
        map.setMultiTouchControls(true);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    GeoPoint currentLocation = new GeoPoint(latitude, longitude);

                    if (marker != null) {
                        marker.setPosition(currentLocation);
                    } else {
                        marker = new Marker(map);
                        marker.setPosition(currentLocation);
                        marker.setIcon(ContextCompat.getDrawable(CotizarActivity.this, R.drawable.yo_gps_icon));
                        map.getOverlays().add(marker);
                    }

                    map.getController().setCenter(currentLocation);
                    map.getController().setZoom(16);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }
}