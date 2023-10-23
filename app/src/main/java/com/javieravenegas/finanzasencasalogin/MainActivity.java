package com.javieravenegas.finanzasencasalogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText correo;
    private Button registrarse;
    private Button iniciar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registrarse = (Button) findViewById(R.id.btnRegistrarse);
        iniciar = (Button)findViewById(R.id.btnLogin);
        correo = (EditText)findViewById(R.id.txtCorreo);

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegistrarseActivity.class);
                startActivity(i);
            }
        });

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                Intent in = new Intent(MainActivity.this, InicioActivity.class);
                in.putExtra("correo", correo.getText().toString());
                startActivity(in);
            }
        });
    }
}