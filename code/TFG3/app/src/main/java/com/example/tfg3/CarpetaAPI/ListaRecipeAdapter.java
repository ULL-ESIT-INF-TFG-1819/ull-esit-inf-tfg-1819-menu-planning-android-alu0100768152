package com.example.tfg3.CarpetaAPI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tfg3.R;

import java.util.ArrayList;

public class ListaRecipeAdapter extends RecyclerView.Adapter<ListaRecipeAdapter.ViewHolder> implements View.OnClickListener{

    private ArrayList<Recipe> dataset;
    private Context context;
    private View.OnClickListener listener;


    public ListaRecipeAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe p = dataset.get(position);
        holder.nombreTextView.setText(p.getRecipe().getLabel());

        Glide.with(context)
                .load(p.getRecipe().getImage())
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.fotoImageView);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {

        if (listener!=null){
            listener.onClick(view);
        }
        // Log.i("RECETA", "on cliiiiickkkk"+getItem(position));

        // Toast.makeText(this.getContext(),"", Toast.LENGTH_SHORT).show();
    }

    public void adicionarListaRecipe(ArrayList<Recipe> listaRecipe) {
        dataset.addAll(listaRecipe);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView fotoImageView;
        private TextView nombreTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            fotoImageView = (ImageView) itemView.findViewById(R.id.fotoImageView);
            nombreTextView = (TextView) itemView.findViewById(R.id.nombreTextView);
        }
    }
}
