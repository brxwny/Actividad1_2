package com.javieravenegas.finanzasencasalogin;

import android.content.Context;
import android.content.Intent;
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
    private Context context;
    private LayoutInflater inflater;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtCvInicio;
        ImageView imgCvInicio;

        public MyViewHolder(View itemView){
            super(itemView);
            this.txtCvInicio = (TextView) itemView.findViewById(R.id.txtCvInicio);
            this.imgCvInicio = (ImageView) itemView.findViewById(R.id.imgCvInicio);
        }
    }

    public CustomAdapter(Context context, ArrayList<DataModel> data) {
        this.context = context;
        this.dataSet = data;
        this.inflater = LayoutInflater.from(context);
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

        final int finalListPosition = listPosition;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalListPosition >= 0 && finalListPosition < MyData.interactInArray.length){
                    Class<?> targetActivityClass = MyData.interactInArray[finalListPosition];

                    Intent i = new Intent(v.getContext(), targetActivityClass);
                    v.getContext().startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
