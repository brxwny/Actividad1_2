package com.javieravenegas.finanzasencasalogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Configuraciones extends AppCompatActivity {

    private EditText pruebatxt;
    private Button btnSaveCambios, btnClose;
    private String usuarioUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuraciones);

        btnSaveCambios = findViewById(R.id.btnSaveConfig);
        btnClose = findViewById(R.id.btnCerrarSesion);
        pruebatxt = findViewById(R.id.txtNombreConfig);
        capturarId();
        pruebatxt.setText(usuarioUid);

        btnSaveCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarDatos();
                Toast.makeText(Configuraciones.this, "Cambios guardados con éxito", Toast.LENGTH_SHORT).show();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarDatos();
                Intent intent = new Intent(Configuraciones.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void actualizarDatos(){

    }

    private void eliminarDatos(){
        try{
            Toast.makeText(Configuraciones.this, "Sesión cerrada exitosamente", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(Configuraciones.this, "Error al cerrar sesión", Toast.LENGTH_LONG).show();
        }
    }

    private void capturarId(){
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        usuarioUid = sharedPreferences.getString("uid", "");
        pruebatxt.setText(usuarioUid);
    }
}