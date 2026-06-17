package it.unibo.zoo.model.entity;

public class Dieta {

    private int idSpecie;  // PK FK
    private int idTipoCibo;  // PK FK
    private double quantitaKgGiorno;

    public Dieta() {}

    public Dieta(int idSpecie, int idTipoCibo, double quantitaKgGiorno) {
        this.idSpecie = idSpecie;
        this.idTipoCibo = idTipoCibo;
        this.quantitaKgGiorno = quantitaKgGiorno;
    }

    public int getIdSpecie() { return idSpecie; }
    public void setIdSpecie(int idSpecie) { this.idSpecie = idSpecie; }

    public int getIdTipoCibo() { return idTipoCibo; }
    public void setIdTipoCibo(int idTipoCibo) { this.idTipoCibo = idTipoCibo; }

    public double getQuantitaKgGiorno() { return quantitaKgGiorno; }
    public void setQuantitaKgGiorno(double quantitaKgGiorno) { this.quantitaKgGiorno = quantitaKgGiorno; }

    @Override
    public String toString() {
        return "Dieta{idSpecie=" + idSpecie + ", idTipoCibo=" + idTipoCibo + ", quantitaKgGiorno=" + quantitaKgGiorno + "}";
    }
}