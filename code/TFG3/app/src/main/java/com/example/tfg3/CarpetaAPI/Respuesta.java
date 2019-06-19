package com.example.tfg3.CarpetaAPI;

import java.util.ArrayList;

public class Respuesta {
    private ArrayList<Recipe> hits;
    private int count;

    public void setHits(ArrayList<Recipe> hits) {
        this.hits = hits;
    }

    public ArrayList<Recipe> getHits() {
        return hits;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
