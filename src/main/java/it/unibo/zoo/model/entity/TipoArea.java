package it.unibo.zoo.model.entity;

public class TipoArea {

    private int idTipoArea;
    private String nome;
    private String descrizione;

    public TipoArea() {}

    public TipoArea(int idTipoArea, String nome, String descrizione) {
        this.idTipoArea = idTipoArea;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public int getIdTipoArea() { return idTipoArea; }
    public void setIdTipoArea(int idTipoArea) { this.idTipoArea = idTipoArea; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    @Override
    public String toString() {
        return "TipoArea{idTipoArea=" + idTipoArea + ", nome='" + nome + "'}";
    }
}