package it.unibo.zoo.model.entity;

public class Recinto {

    private int idRecinto;
    private int capienzaMassima;
    private int idArea;  // FK
    private int idTipoRecinto;  // FK

    public Recinto() {}

    public Recinto(int idRecinto, int capienzaMassima, int idArea, int idTipoRecinto) {
        this.idRecinto = idRecinto;
        this.capienzaMassima = capienzaMassima;
        this.idArea = idArea;
        this.idTipoRecinto = idTipoRecinto;
    }

    public Recinto(int capienzaMassima, int idArea, int idTipoRecinto) {
        this.capienzaMassima = capienzaMassima;
        this.idArea = idArea;
        this.idTipoRecinto = idTipoRecinto;
    }

    public int getIdRecinto() { return idRecinto; }
    public void setIdRecinto(int idRecinto) { this.idRecinto = idRecinto; }

    public int getCapienzaMassima() { return capienzaMassima; }
    public void setCapienzaMassima(int capienzaMassima) { this.capienzaMassima = capienzaMassima; }

    public int getIdArea() { return idArea; }
    public void setIdArea(int idArea) { this.idArea = idArea; }

    public int getIdTipoRecinto() { return idTipoRecinto; }
    public void setIdTipoRecinto(int idTipoRecinto) { this.idTipoRecinto = idTipoRecinto; }

    @Override
    public String toString() {
        return "Recinto{idRecinto=" + idRecinto + ", idArea=" + idArea + "}";
    }
}