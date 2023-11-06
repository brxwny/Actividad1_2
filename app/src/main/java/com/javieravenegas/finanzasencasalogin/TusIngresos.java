package com.javieravenegas.finanzasencasalogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TusIngresos extends AppCompatActivity {

    private Button addIngreso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tus_ingresos);

        addIngreso = (Button) findViewById(R.id.btnAddIngreso);
        addIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TusIngresos.this, AddIngreso.class);
                startActivity(in);
            }
        });
    }
}