package com.javieravenegas.finanzasencasalogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterIn extends RecyclerView.Adapter<AdapterIn.MyViewHolder>{
    private ArrayList<DataModelI> dataSet;
    private Context context;
    private LayoutInflater inflater;

    public AdapterIn(ArrayList<DataModelI> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardsingresos_layout, parent, false);
        AdapterIn.MyViewHolder myViewHolder = new AdapterIn.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int listPosition) {
        TextView txtNomInCV = holder.txtNomInCV;
        TextView txtMontoInCV = holder.txtMontoInCV;

        txtNomInCV.setText(dataSet.get(listPosition).getNombre());
        txtMontoInCV.setText("$" + dataSet.get(listPosition).getMonto());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtNomInCV;
        TextView txtMontoInCV;

        public MyViewHolder(View itemView){
            super(itemView);
            this.txtNomInCV = itemView.findViewById(R.id.txtNomInCV);
            this.txtMontoInCV = itemView.findViewById(R.id.txtMontoInCV);
        }
    }
}
