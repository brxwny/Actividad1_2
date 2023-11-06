package com.javieravenegas.finanzasencasalogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class TusFinanzas extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;
    private Button addCat;
    private Button cotizar;
    private  Button inversiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_tus_finanzas);

        recyclerView = (RecyclerView) findViewById(R.id.cv_cat_finanzas);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.catFinCvArray.length; i++) {
            data.add(new DataModel(MyData.catFinCvArray[i], MyData.imgCatFinArray[i]));
        }
        adapter = new CusAdapterFinanzas(this, data);
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
}