package com.javieravenegas.finanzasencasalogin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class InicioActivity extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;

    private Bundle bundle;
    private TextView fuenteDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_inicio);

        fuenteDatos = (TextView)findViewById(R.id.txtFuenteIn);
        bundle = getIntent().getExtras();
        String correoUser = bundle.getString("correo");
        fuenteDatos.append("Basadas en datos correspondientes a la cuenta de " + correoUser);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.nombreInArray.length; i++) {
            data.add(new DataModel(MyData.nombreInArray[i], MyData.drawableInArray[i]));
        }
        adapter = new CustomAdapter(this, data);
        recyclerView.setAdapter(adapter);
    }
}