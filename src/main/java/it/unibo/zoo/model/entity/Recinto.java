package it.unibo.zoo.model.entity;

public class Recinto {

    private int idRecinto;
    private int idTipoRecinto;  // FK → Tipi_Recinto
    private int idArea;         // FK → Aree

    public Recinto() {}

    public Recinto(int idRecinto, int idTipoRecinto, int idArea) {
        this.idRecinto = idRecinto;
        this.idTipoRecinto = idTipoRecinto;
        this.idArea = idArea;
    }

    public int getIdRecinto() { return idRecinto; }
    public void setIdRecinto(int idRecinto) { this.idRecinto = idRecinto; }

    public int getIdTipoRecinto() { return idTipoRecinto; }
    public void setIdTipoRecinto(int idTipoRecinto) { this.idTipoRecinto = idTipoRecinto; }

    public int getIdArea() { return idArea; }
    public void setIdArea(int idArea) { this.idArea = idArea; }

    @Override
    public String toString() {
        return "Recinto{idRecinto=" + idRecinto + ", idArea=" + idArea + "}";
    }
}