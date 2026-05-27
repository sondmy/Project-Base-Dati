package it.unibo.zoo.model.entity;

import java.time.LocalDateTime;

public class Scontrino {

    private int idScontrino;
    private LocalDateTime dataOra;
    private String nomeGruppo;      // nullable
    private Integer numPersone;     // nullable
    private double totale;

    public Scontrino() {}

    public Scontrino(int idScontrino, LocalDateTime dataOra, String nomeGruppo,
                     Integer numPersone, double totale) {
        this.idScontrino = idScontrino;
        this.dataOra = dataOra;
        this.nomeGruppo = nomeGruppo;
        this.numPersone = numPersone;
        this.totale = totale;
    }

    public int getIdScontrino() { return idScontrino; }
    public void setIdScontrino(int idScontrino) { this.idScontrino = idScontrino; }

    public LocalDateTime getDataOra() { return dataOra; }
    public void setDataOra(LocalDateTime dataOra) { this.dataOra = dataOra; }

    public String getNomeGruppo() { return nomeGruppo; }
    public void setNomeGruppo(String nomeGruppo) { this.nomeGruppo = nomeGruppo; }

    public Integer getNumPersone() { return numPersone; }
    public void setNumPersone(Integer numPersone) { this.numPersone = numPersone; }

    public double getTotale() { return totale; }
    public void setTotale(double totale) { this.totale = totale; }

    @Override
    public String toString() {
        return "Scontrino{idScontrino=" + idScontrino + ", totale=" + totale + "}";
    }
}
