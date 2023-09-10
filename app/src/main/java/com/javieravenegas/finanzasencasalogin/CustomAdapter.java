package com.javieravenegas.finanzasencasalogin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    private ArrayList<DataModel> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtCvInicio;
        ImageView imgCvInicio;

        public MyViewHolder(View itemView){
            super(itemView);
            this.txtCvInicio = (TextView) itemView.findViewById(R.id.txtCvInicio);
            this.imgCvInicio = (ImageView) itemView.findViewById(R.id.imgCvInicio);
        }
    }

    public CustomAdapter(ArrayList<DataModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardsinicio_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        TextView txtCvInicio = holder.txtCvInicio;
        ImageView imgCvInicio = holder.imgCvInicio;

        txtCvInicio.setText(dataSet.get(listPosition).getNombre());
        imgCvInicio.setImageResource(dataSet.get(listPosition).getImagen());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
