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
import com.javieravenegas.finanzasencasalogin.models.Finanzas;
import com.javieravenegas.finanzasencasalogin.models.Ingreso;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddIngreso extends AppCompatActivity {
    private Button finalizar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String uiduser, uid, uidcat, ingresan;
    private EditText nombreIn, desIn, montoIn;
    private long fechaIn;
    private int addF;
    private Spinner spinner;
    private List<String> paths = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_add_ingreso);
        capturarId();
        inicializarFirebase();
        uidcat = "";

        nombreIn = findViewById(R.id.txtAddNomIn);
        desIn = findViewById(R.id.txtAddDesIn);
        montoIn = findViewById(R.id.intMontoAddIn);
        finalizar = findViewById(R.id.btn_FinalizarAddIn);
        spinner = findViewById(R.id.spinnerCatIn);
        uid = UUID.randomUUID().toString();
        fechaIn = System.currentTimeMillis();
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
                Toast.makeText(AddIngreso.this, "Error al seleccionar categoría", Toast.LENGTH_LONG).show();
            }
        });

        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nombreIn.getText().toString().equals("")||desIn.getText().toString().equals("")||montoIn.getText().toString().equals("")){
                    Toast.makeText(AddIngreso.this, "Debe completar los campos obligatorios", Toast.LENGTH_LONG).show();
                }else{
                    try {
                        ingresan = montoIn.getText().toString();
                        addF = Integer.parseInt(ingresan);
                        crearNuevoRegistro();
                        actualizarFinanzas();
                        Intent i = new Intent(AddIngreso.this, TusIngresos.class);
                        startActivity(i);
                    }catch (Exception e){
                        Toast.makeText(AddIngreso.this, "Error al añadir ingreso", Toast.LENGTH_LONG).show();
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
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Categoria categoria = snapshot.getValue(Categoria.class);
                        if (categoria != null) {
                            String nombreCategoria = categoria.getNombre();
                            paths.add(nombreCategoria);
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AddIngreso.this, R.layout.spinner_personalizado, paths);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                } else {
                    Toast.makeText(AddIngreso.this, "No se encontraron categorías", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddIngreso.this, "Error en la consulta a la base de datos", Toast.LENGTH_LONG).show();
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
                        }
                    }
                } else {
                    Toast.makeText(AddIngreso.this, "No se encontró la categoría", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddIngreso.this, "Error en la consulta a la base de datos", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void crearNuevoRegistro(){
        Ingreso i = new Ingreso();
        i.setUid(uid);
        i.setUiduser(uiduser);
        i.setFecha(fechaIn);
        i.setNombre(nombreIn.getText().toString());
        i.setDescripcion(desIn.getText().toString());
        i.setMonto(montoIn.getText().toString().trim());
        i.setUidcat(uidcat);

        databaseReference.child("Ingresos").child(i.getUid()).setValue(i);
    }

    public void actualizarFinanzas(){
        DatabaseReference finanzasRef = databaseReference.child("Finanzas").child(uiduser);

        finanzasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Finanzas finanzas = dataSnapshot.getValue(Finanzas.class);
                    if (finanzas != null) {
                        String inT = finanzas.getIngresos();
                        if (inT.equals("")) {
                            databaseReference.child("Finanzas").child(uiduser).child("ingresos").setValue(ingresan);
                        } else {
                            int ingresosTotales = Integer.parseInt(inT);
                            int addFinanzas = ingresosTotales + addF;
                            String ingresos = String.valueOf(addFinanzas);
                            databaseReference.child("Finanzas").child(uiduser).child("ingresos").setValue(ingresos);
                            databaseReference.child("Finanzas").child(uiduser).child("uid").setValue(finanzas.getUid());
                            databaseReference.child("Finanzas").child(uiduser).child("uiduser").setValue(finanzas.getUiduser());
                            databaseReference.child("Finanzas").child(uiduser).child("presupuesto").setValue(finanzas.getPresupuesto());
                            databaseReference.child("Finanzas").child(uiduser).child("gastos").setValue(finanzas.getGastos());
                            databaseReference.child("Finanzas").child(uiduser).child("fecha").setValue(fechaIn);
                        }
                    }
                } else {
                    Toast.makeText(AddIngreso.this, "No se encontró el registro de Finanzas", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar el error...
            }
        });
    }
}