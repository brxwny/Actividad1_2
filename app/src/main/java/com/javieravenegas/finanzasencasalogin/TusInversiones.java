package com.javieravenegas.finanzasencasalogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TusInversiones extends AppCompatActivity {

    private Button addInversion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tus_inversiones);

        addInversion = (Button) findViewById(R.id.btnAddInversion);
        addInversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TusInversiones.this, AddInversion.class);
                startActivity(in);
            }
        });
    }
}