package it.unibo.zoo.model.entity;

public class Area {

    private int idArea;
    private String nome;
    private int metratura;
    private int idTipoArea;  // FK

    public Area() {}

    public Area(int idArea, String nome, int metratura, int idTipoArea) {
        this.idArea = idArea;
        this.nome = nome;
        this.metratura = metratura;
        this.idTipoArea = idTipoArea;
    }

    public Area(String nome, int metratura, int idTipoArea) {
        this.nome = nome;
        this.metratura = metratura;
        this.idTipoArea = idTipoArea;
    }

    public int getIdArea() { return idArea; }
    public void setIdArea(int idArea) { this.idArea = idArea; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getMetratura() { return metratura; }
    public void setMetratura(int metratura) { this.metratura = metratura; }

    public int getIdTipoArea() { return idTipoArea; }
    public void setIdTipoArea(int idTipoArea) { this.idTipoArea = idTipoArea; }

    @Override
    public String toString() {
        return "Area{idArea=" + idArea + ", nome=" + nome + ", metratura=" + metratura + "}";
    }
}