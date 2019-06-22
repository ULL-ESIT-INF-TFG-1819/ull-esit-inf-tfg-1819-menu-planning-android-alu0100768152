package com.example.tfg3;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterListaCompra extends RecyclerView.Adapter<AdapterListaCompra.ViewHolderDatos> {

    ArrayList<String> listDatos;

    public AdapterListaCompra(ArrayList<String> listDatos) {
        this.listDatos = listDatos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista,null,false);
        return new ViewHolderDatos(view);
    }
    //getContext()).inflate(R.layout.item_lista,null,false);
    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignarDatos(listDatos.get(position));

    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView dato;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            dato= (CheckBox) itemView.findViewById(R.id.idIngrediente);
        }

        public void asignarDatos(String s) {
            dato.setText(s);
        }
    }
}
