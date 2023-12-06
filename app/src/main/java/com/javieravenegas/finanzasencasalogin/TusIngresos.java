package com.javieravenegas.finanzasencasalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TusIngresos extends AppCompatActivity {

    private Button addIngreso;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String uiduser;
    private RecyclerView recyclerView;
    private AdapterIn adapter;
    private ArrayList<DataModelI> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_tus_ingresos);
        capturarId();
        inicializarFirebase();

        addIngreso = (Button) findViewById(R.id.btnAddIngreso);
        recyclerView = findViewById(R.id.my_recycler_view_ingresos);
        addIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TusIngresos.this, AddIngreso.class);
                startActivity(in);
            }
        });

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<>();

        databaseReference.child("Ingresos").orderByChild("uiduser").equalTo(uiduser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ingresoSnapshot : dataSnapshot.getChildren()) {
                    String nombre = ingresoSnapshot.child("nombre").getValue(String.class);
                    String monto = ingresoSnapshot.child("monto").getValue(String.class);

                    data.add(new DataModelI(nombre, monto));
                }

                adapter = new AdapterIn(data, TusIngresos.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TusIngresos.this, "Error en la consulta a la base de datos", Toast.LENGTH_LONG).show();
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