package com.example.homecourseandroid.Model;

public class CarritoDeCompras {
    private String idCur;
    private String nomCur;
    private String CateCur;
    private Double precioCur;

    public CarritoDeCompras(String idCur, String nomCur, String cateCur, Double precioCur) {
        this.idCur = idCur;
        this.nomCur = nomCur;
        CateCur = cateCur;
        this.precioCur = precioCur;
    }
    public CarritoDeCompras() {
    }

    public String getIdCur() {
        return idCur;
    }

    public void setIdCur(String idCur) {
        this.idCur = idCur;
    }

    public String getNomCur() {
        return nomCur;
    }

    public void setNomCur(String nomCur) {
        this.nomCur = nomCur;
    }

    public String getCateCur() {
        return CateCur;
    }

    public void setCateCur(String cateCur) {
        CateCur = cateCur;
    }

    public Double getPrecioCur() {
        return precioCur;
    }

    public void setPrecioCur(Double precioCur) {
        this.precioCur = precioCur;
    }
}
