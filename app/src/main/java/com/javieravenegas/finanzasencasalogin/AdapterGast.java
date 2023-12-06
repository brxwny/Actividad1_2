package com.javieravenegas.finanzasencasalogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterGast extends RecyclerView.Adapter<AdapterGast.MyViewHolder>{
    private ArrayList<DataModelG> dataSet;
    private Context context;
    private LayoutInflater inflater;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardsgastos_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        TextView txtNomGasCV = holder.txtNomGasCV;
        TextView txtMontoGasCV = holder.txtMontoGasCV;

        txtNomGasCV.setText(dataSet.get(listPosition).getNombre());
        txtMontoGasCV.setText("$" + dataSet.get(listPosition).getMonto());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtNomGasCV;
        TextView txtMontoGasCV;

        public MyViewHolder(View itemView){
            super(itemView);
            this.txtNomGasCV = itemView.findViewById(R.id.txtNomGasCV);
            this.txtMontoGasCV = itemView.findViewById(R.id.txtMontoGasCV);
        }
    }

    public AdapterGast(ArrayList<DataModelG> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
        this.inflater = LayoutInflater.from(context);;
    }
}
