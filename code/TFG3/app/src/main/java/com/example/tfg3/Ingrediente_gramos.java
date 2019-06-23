package com.example.tfg3;

public class Ingrediente_gramos {

    private String ingrediente;
    private double gramos;
    private int id;

    public Ingrediente_gramos(String ingrediente, double gramos, int id) {
        this.ingrediente = ingrediente;
        this.gramos = gramos;
        this.id = id;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public double getGramos() {
        return gramos;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    public void setGramos(double gramos) {
        this.gramos = gramos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
