package com.javieravenegas.finanzasencasalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.javieravenegas.finanzasencasalogin.models.Categoria;
import com.javieravenegas.finanzasencasalogin.models.Gasto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddGasto extends AppCompatActivity {
    private Button finalizar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String uiduser, uid, uidcat;
    private EditText nombreGas, desGast, montoGas;
    private long fechaGast;

    private Spinner spinner;
    private List<String> paths = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_add_gasto);
        capturarId();
        inicializarFirebase();

        nombreGas = findViewById(R.id.txtAddNomGast);
        desGast = findViewById(R.id.txtAddDesGast);
        montoGas = findViewById(R.id.intMontoAddGast);
        finalizar = findViewById(R.id.btn_FinalizarAddGast);
        spinner = findViewById(R.id.spinnerCatGast);
        uid = UUID.randomUUID().toString();
        fechaGast = System.currentTimeMillis();
        cargarCat();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View v, int position, long id) {
                String selectedCategory = paths.get(position);
                if (!selectedCategory.equals("Categoría")) {
                    obtenerUidCategoria(selectedCategory);
                }else{
                    uidcat = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddGasto.this, "Error al seleccionar categoría", Toast.LENGTH_LONG).show();
            }
        });
        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nombreGas.getText().toString().equals("")||desGast.getText().toString().equals("")||montoGas.getText().toString().equals("")){
                    Toast.makeText(AddGasto.this, "Debe completar los campos obligatorios", Toast.LENGTH_LONG).show();
                }else{
                    try {
                        crearNuevoRegistro();
                        Intent i = new Intent(AddGasto.this, TusGastos.class);
                        startActivity(i);
                    }catch (Exception e){
                        Toast.makeText(AddGasto.this, "Error al añadir gasto", Toast.LENGTH_LONG).show();
                    }
                }
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

    private void cargarCat(){
        databaseReference.child("Categoria").orderByChild("uiduser").equalTo(uiduser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    paths.clear();
                    paths.add("Categoría");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Categoria categoria = snapshot.getValue(Categoria.class);
                        if (categoria != null) {
                            String nombreCategoria = categoria.getNombre();
                            paths.add(nombreCategoria);
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AddGasto.this, R.layout.spinner_personalizado, paths);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                } else {
                    Toast.makeText(AddGasto.this, "No se encontraron categorías", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddGasto.this, "Error en la consulta a la base de datos", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void obtenerUidCategoria(final String nombreCategoria){
        databaseReference.child("Categoria").orderByChild("nombre").equalTo(nombreCategoria).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Categoria categoria = snapshot.getValue(Categoria.class);
                        if (categoria != null && categoria.getUiduser().equals(uiduser)) {
                            uidcat = categoria.getUid();
                            Toast.makeText(AddGasto.this, "Seleccionaste: " + nombreCategoria + " con uid: " + uidcat, Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(AddGasto.this, "No se encontró la categoría", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddGasto.this, "Error en la consulta a la base de datos", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void crearNuevoRegistro(){
        Gasto g = new Gasto();
        g.setUid(uid);
        g.setUiduser(uiduser);
        g.setFecha(fechaGast);
        g.setNombre(nombreGas.getText().toString());
        g.setDescripcion(desGast.getText().toString());
        g.setMonto(montoGas.getText().toString().trim());
        g.setUidcat(uidcat);

        databaseReference.child("Gastos").child(g.getUid()).setValue(g);
    }
}