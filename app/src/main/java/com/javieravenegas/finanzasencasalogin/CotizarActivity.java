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

    private Marker markerTiendas;
    private Marker markerTiendasDos;
    private Marker markerTiendasTr;
    private Marker markerBancoUn;
    private Marker markerBancoDos;
    private Marker markerPanaderia;
    private Marker markerHosp;
    private Marker restaurantesUn;
    private Marker restaurantesDos;

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
                        marker.setTitle("Tu ubicación");
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
        //Tiendas
        GeoPoint tienda1 = new GeoPoint(-33.478847186290714, -70.75178146325877);
        GeoPoint tienda2 = new GeoPoint(-33.471691934359754, -70.75820883432218);
        GeoPoint banco1 = new GeoPoint(-33.485782242646586, -70.75081318640974);
        GeoPoint banco2 = new GeoPoint(-33.48104285612204, -70.751268554442);
        GeoPoint pan = new GeoPoint(-33.476031252465305, -70.74886503154745);
        GeoPoint hosp1 = new GeoPoint(-33.47787474756491, -70.75434746689999);
        GeoPoint rest1 = new GeoPoint(-33.47442726634496, -70.75374601753252);
        GeoPoint rest2 = new GeoPoint(-33.45152018610466, -70.66575420195265);
        GeoPoint tienda3 = new GeoPoint(-33.45162598593174, -70.66417334711143);

        markerTiendas = new Marker(map);
        markerTiendas.setPosition(tienda1);
        markerTiendas.setIcon(ContextCompat.getDrawable(CotizarActivity.this, R.drawable.tiendas_icon4));
        markerTiendas.setTitle("Jumbo Mall Arauco Maipú");
        map.getOverlays().add(markerTiendas);

        markerBancoUn = new Marker(map);
        markerBancoUn.setPosition(banco1);
        markerBancoUn.setIcon(ContextCompat.getDrawable(CotizarActivity.this, R.drawable.bancos_icon));
        markerBancoUn.setTitle("Bancoestado Maipú Américo Vespucio");
        map.getOverlays().add(markerBancoUn);

        markerBancoDos = new Marker(map);
        markerBancoDos.setPosition(banco2);
        markerBancoDos.setIcon(ContextCompat.getDrawable(CotizarActivity.this, R.drawable.bancos_icon));
        markerBancoDos.setTitle("Banco de Chile Mall Arauco Maipú");
        map.getOverlays().add(markerBancoDos);

        markerTiendasDos = new Marker(map);
        markerTiendasDos.setPosition(tienda2);
        markerTiendasDos.setIcon(ContextCompat.getDrawable(CotizarActivity.this, R.drawable.tiendas_icon4));
        markerTiendasDos.setTitle("Supermercado Lider Maipú Américo Vespucio");
        map.getOverlays().add(markerTiendasDos);

        markerPanaderia = new Marker(map);
        markerPanaderia.setPosition(pan);
        markerPanaderia.setIcon(ContextCompat.getDrawable(CotizarActivity.this, R.drawable.panaderias_icon));
        markerPanaderia.setTitle("Panadería La araucaria");
        map.getOverlays().add(markerPanaderia);

        markerHosp = new Marker(map);
        markerHosp.setPosition(hosp1);
        markerHosp.setIcon(ContextCompat.getDrawable(CotizarActivity.this, R.drawable.hospitales_icon));
        markerHosp.setTitle("Clínica INDISA Maipú");
        map.getOverlays().add(markerHosp);

        restaurantesUn = new Marker(map);
        restaurantesUn.setPosition(rest1);
        restaurantesUn.setIcon(ContextCompat.getDrawable(CotizarActivity.this, R.drawable.restaurantes_icon));
        restaurantesUn.setTitle("Tienda de Sushi INACO Sushi");
        map.getOverlays().add(restaurantesUn);

        markerTiendasTr = new Marker(map);
        markerTiendasTr.setPosition(tienda3);
        markerTiendasTr.setIcon(ContextCompat.getDrawable(CotizarActivity.this, R.drawable.tiendas_icon4));
        markerTiendasTr.setTitle("Supermercado Santa Isabel");
        map.getOverlays().add(markerTiendasTr);

        restaurantesDos = new Marker(map);
        restaurantesDos.setPosition(rest2);
        restaurantesDos.setIcon(ContextCompat.getDrawable(CotizarActivity.this, R.drawable.restaurantes_icon));
        restaurantesDos.setTitle("Tienda de completos El Italiano Veloz");
        map.getOverlays().add(restaurantesDos);
    }
}