package com.javieravenegas.finanzasencasalogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import org.osmdroid.views.MapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.config.Configuration;

public class CotizarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotizar);

        // Inicializa la configuración de OSM
        Configuration.getInstance().load(getApplicationContext(), getPreferences(MODE_PRIVATE));

        // Inicializa el mapa
        MapView map = findViewById(R.id.mapview);
        map.setMultiTouchControls(true);

        // Establece la ubicación inicial (por ejemplo, Nueva York)
        GeoPoint startPoint = new GeoPoint(-33.4489, -70.6693);
        map.getController().setCenter(startPoint);
        map.getController().setZoom(12);
    }
}