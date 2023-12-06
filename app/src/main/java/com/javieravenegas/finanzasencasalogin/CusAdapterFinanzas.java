package com.javieravenegas.finanzasencasalogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CusAdapterFinanzas extends RecyclerView.Adapter<CusAdapterFinanzas.MyViewHolder>{

    private ArrayList<DataModelF> dataSet;
    private Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardsfinanzas_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        TextView txtCvFinanzas = holder.txtCVFinanzas;
        ImageView imgCvFinanzas = holder.imgCvFinanzas;

        txtCvFinanzas.setText(dataSet.get(position).getNombre());

        Picasso.get().load(dataSet.get(position).getImagenUrl()).into(imgCvFinanzas);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtCVFinanzas;
        ImageView imgCvFinanzas;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtCVFinanzas = itemView.findViewById(R.id.txtCvFinanzas);
            this.imgCvFinanzas = itemView.findViewById(R.id.imgCvFinanzas);
        }
    }

    public CusAdapterFinanzas(ArrayList<DataModelF> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
    }
}
