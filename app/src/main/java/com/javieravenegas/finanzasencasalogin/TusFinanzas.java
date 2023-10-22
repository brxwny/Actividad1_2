package com.javieravenegas.finanzasencasalogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class TusFinanzas extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }
}