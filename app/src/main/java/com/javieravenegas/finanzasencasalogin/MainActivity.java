package com.javieravenegas.finanzasencasalogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText correo, pass;
    private Button registrarse, iniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registrarse = (Button) findViewById(R.id.btnRegistrarse);
        iniciar = (Button)findViewById(R.id.btnLogin);
        correo = (EditText)findViewById(R.id.txtCorreo);
        pass = findViewById(R.id.txtPassword);

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
                try{
                    if(correo.getText().toString().equals("")||pass.getText().toString().equals("")){
                        Toast.makeText(MainActivity.this, "Ingrese sus credenciales", Toast.LENGTH_LONG).show();
                    }else{
                        Intent in = new Intent(MainActivity.this, InicioActivity.class);
                        in.putExtra("correo", correo.getText().toString());
                        startActivity(in);
                    }
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Algo salió mal", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}