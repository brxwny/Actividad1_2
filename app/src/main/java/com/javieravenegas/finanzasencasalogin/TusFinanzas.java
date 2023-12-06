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

public class TusFinanzas extends AppCompatActivity {

    private CusAdapterFinanzas adapter;
    private ArrayList<DataModelF> data;
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String uiduser;
    private Button addCat;
    private Button cotizar;
    private  Button inversiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_tus_finanzas);
        capturarId();
        inicializarFirebase();

        recyclerView =findViewById(R.id.cv_cat_finanzas);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<>();
        obtenerDatosDeFirebase();
        adapter = new CusAdapterFinanzas(data, this);
        recyclerView.setAdapter(adapter);

        addCat = (Button) findViewById(R.id.btnAddCategoria);

        addCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vie) {
                Intent in = new Intent(TusFinanzas.this, AddCat.class);
                startActivity(in);
            }
        });

        cotizar = (Button) findViewById(R.id.btnCotizar);
        cotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TusFinanzas.this, CotizarActivity.class);
                startActivity(in);
            }
        });

        inversiones = (Button) findViewById(R.id.btnInversiones);
        inversiones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TusFinanzas.this, TusInversiones.class);
                startActivity(in);
            }
        });
    }

    private void obtenerDatosDeFirebase(){
        databaseReference.child("Categoria").orderByChild("uiduser").equalTo(uiduser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot categoriaSnapshot : dataSnapshot.getChildren()) {
                    String nombre = categoriaSnapshot.child("nombre").getValue(String.class);
                    String imageUrl = categoriaSnapshot.child("photo").getValue(String.class);

                    data.add(new DataModelF(nombre, imageUrl));
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TusFinanzas.this, "Error en la consulta a la base de datos", Toast.LENGTH_LONG).show();
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