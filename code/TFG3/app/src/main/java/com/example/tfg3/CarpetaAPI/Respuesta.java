package com.example.tfg3.CarpetaAPI;

import java.util.ArrayList;

public class Respuesta {
    private ArrayList<Recipe> hits;

    public void setHits(ArrayList<Recipe> hits) {
        this.hits = hits;
    }

    public ArrayList<Recipe> getHits() {
        return hits;
    }
}
