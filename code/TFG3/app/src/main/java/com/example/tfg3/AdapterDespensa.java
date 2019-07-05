package com.example.tfg3;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterDespensa extends RecyclerView.Adapter<AdapterDespensa.ViewHolderDatos>
        implements View.OnClickListener {

    ArrayList<Ingrediente_gramos> listDatos;
    private View.OnClickListener listener;

    public AdapterDespensa(ArrayList<Ingrediente_gramos> listDatos) {
        this.listDatos = listDatos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_despensa,null,false);
        view.setOnClickListener(this);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignarDatos(listDatos.get(position).getIngrediente()+ " ("+listDatos.get(position).getGramos()+"g)");
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView dato;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            dato= (TextView) itemView.findViewById(R.id.itemDespensa);
        }

        public void asignarDatos(String s) {
            dato.setText(s);
        }
    }
}
