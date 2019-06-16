package com.example.tfg3.CarpetaAPI;

import java.util.ArrayList;

public class Contenido {

    private String label;
    private String image;
    private String url;
    private String shareAs;
    private int yield;
    private float calories;
    private ArrayList<Ingredientes> ingredients;
    private Nutrientes totalNutrients;


    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setShareAs(String shareAs) {
        this.shareAs = shareAs;
    }

    public void setYield(int yield) {
        this.yield = yield;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public String getUrl() {
        return url;
    }

    public String getShareAs() {
        return shareAs;
    }

    public int getYield() {
        return yield;
    }

    public float getCalories() {
        return calories;
    }

    public ArrayList<Ingredientes> getIngredients() {
        return ingredients;
    }

    public Nutrientes getTotalNutrients() {
        return totalNutrients;
    }

    public void setIngredients(ArrayList<Ingredientes> ingredients) {
        this.ingredients = ingredients;
    }

    public void setTotalNutrients(Nutrientes totalNutrients) {
        this.totalNutrients = totalNutrients;
    }
}
