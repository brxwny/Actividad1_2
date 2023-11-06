package com.javieravenegas.finanzasencasalogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TusGastos extends AppCompatActivity {

    private Button addCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tus_gastos);

        addCat = (Button) findViewById(R.id.btnAddGasto);
        addCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TusGastos.this, AddGasto.class);
                startActivity(in);
            }
        });
    }
}