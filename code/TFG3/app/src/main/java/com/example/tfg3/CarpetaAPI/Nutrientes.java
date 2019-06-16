package com.example.tfg3.CarpetaAPI;

public class Nutrientes {
    private Cantidad ENERC_KCAL;
    private Cantidad FAT;
    private Cantidad CHOCDF;
    private Cantidad PROCNT;

    public Cantidad getENERC_KCAL() {
        return ENERC_KCAL;
    }

    public Cantidad getFAT() {
        return FAT;
    }

    public Cantidad getCHOCDF() {
        return CHOCDF;
    }

    public Cantidad getPROCNT() {
        return PROCNT;
    }

    public void setENERC_KCAL(Cantidad ENERC_KCAL) {
        this.ENERC_KCAL = ENERC_KCAL;
    }

    public void setFAT(Cantidad FAT) {
        this.FAT = FAT;
    }

    public void setCHOCDF(Cantidad CHOCDF) {
        this.CHOCDF = CHOCDF;
    }

    public void setPROCNT(Cantidad PROCNT) {
        this.PROCNT = PROCNT;
    }
}
