package com.javieravenegas.finanzasencasalogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddIngreso extends AppCompatActivity {
    private Button finalizar;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String uiduser;

    private Spinner spinner;
    private String[] paths = {"Categorías","Libros"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_add_ingreso);

        capturarId();
        inicializarFirebase();

        finalizar = findViewById(R.id.btn_FinalizarAddIn);
        spinner = findViewById(R.id.spinnerCatIn);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddIngreso.this, R.layout.spinner_personalizado,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View v, int position, long id) {
                switch (position) {
                    case 0:
                        Toast.makeText(AddIngreso.this, "Ninguna elegida", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(AddIngreso.this, "Cat 1 elegida", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(AddIngreso.this, "Cat 2 elegida", Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        Toast.makeText(AddIngreso.this, "Cat 3 elegida", Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddIngreso.this, "Error al seleccionar categoría", Toast.LENGTH_LONG).show();
            }
        });

        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddIngreso.this, TusIngresos.class);
                startActivity(i);
            }
        });
    }

    private void capturarId(){
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        uiduser = sharedPreferences.getString("uid", "");
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}