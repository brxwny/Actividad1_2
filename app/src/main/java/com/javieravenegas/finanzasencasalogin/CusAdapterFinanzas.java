package com.javieravenegas.finanzasencasalogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CusAdapterFinanzas extends RecyclerView.Adapter<CusAdapterFinanzas.FinanzasViewHolder>{

    private ArrayList<DataModel> dataSet;
    private Context context;
    private LayoutInflater inflater;

    public CusAdapterFinanzas(Context context, ArrayList<DataModel> data) {
        this.dataSet = data;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public static class FinanzasViewHolder extends RecyclerView.ViewHolder{
        TextView txtCvFinanzas;
        ImageView imgCvFinanzas;

        public FinanzasViewHolder(View itemView){
            super(itemView);
            this.txtCvFinanzas = (TextView) itemView.findViewById(R.id.txtCvFinanzas);
            this.imgCvFinanzas = (ImageView) itemView.findViewById(R.id.imgCvFinanzas);
        }
    }

    @Override
    public FinanzasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardsfinanzas_layout, parent, false);
        CusAdapterFinanzas.FinanzasViewHolder finanzasViewHolder = new CusAdapterFinanzas.FinanzasViewHolder(view);
        return finanzasViewHolder;
    }

    @Override
    public void onBindViewHolder(final CusAdapterFinanzas.FinanzasViewHolder holder, final int listPosition) {
        TextView txtCvFinanzas = holder.txtCvFinanzas;
        ImageView imgCvFinanzas = holder.imgCvFinanzas;

        txtCvFinanzas.setText(dataSet.get(listPosition).getNombre());
        imgCvFinanzas.setImageResource(dataSet.get(listPosition).getImagen());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
