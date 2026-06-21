package it.unibo.zoo.model.entity;

public class Recinto {

    private int idRecinto;
    private String nome;
    private int capienzaMassima;
    private int idArea;  // FK
    private int idTipoRecinto;  // FK

    public Recinto() {
        this.nome = "";
    }

    public Recinto(int idRecinto, String nome, int capienzaMassima, int idArea, int idTipoRecinto) {
        this.idRecinto = idRecinto;
        this.nome = nome;
        this.capienzaMassima = capienzaMassima;
        this.idArea = idArea;
        this.idTipoRecinto = idTipoRecinto;
    }

    public Recinto(int idRecinto, int capienzaMassima, int idArea, int idTipoRecinto) {
        this(idRecinto, "", capienzaMassima, idArea, idTipoRecinto);
    }

    public Recinto(String nome, int capienzaMassima, int idArea, int idTipoRecinto) {
        this(0, nome, capienzaMassima, idArea, idTipoRecinto);
    }

    public int getIdRecinto() { return idRecinto; }
    public void setIdRecinto(int idRecinto) { this.idRecinto = idRecinto; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getCapienzaMassima() { return capienzaMassima; }
    public void setCapienzaMassima(int capienzaMassima) { this.capienzaMassima = capienzaMassima; }

    public int getIdArea() { return idArea; }
    public void setIdArea(int idArea) { this.idArea = idArea; }

    public int getIdTipoRecinto() { return idTipoRecinto; }
    public void setIdTipoRecinto(int idTipoRecinto) { this.idTipoRecinto = idTipoRecinto; }

    @Override
    public String toString() {
        return "Recinto{idRecinto=" + idRecinto + ", nome=" + nome + ", idArea=" + idArea + "}";
    }
}