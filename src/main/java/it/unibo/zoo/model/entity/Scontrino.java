package it.unibo.zoo.model.entity;

import java.time.LocalDate;

public class Scontrino {

    private int idScontrino;
    private LocalDate dataAcquisto;
    private String nomeGruppo;  // nullable
    private Integer numPersone;  // nullable
    private int idUtente;  // FK

    public Scontrino() {}

    public Scontrino(int idScontrino, LocalDate dataAcquisto, String nomeGruppo, Integer numPersone, int idUtente) {
        this.idScontrino = idScontrino;
        this.dataAcquisto = dataAcquisto;
        this.nomeGruppo = nomeGruppo;
        this.numPersone = numPersone;
        this.idUtente = idUtente;
    }
    
    public Scontrino(LocalDate dataAcquisto, String nomeGruppo, Integer numPersone, int idUtente) {
        this.dataAcquisto = dataAcquisto;
        this.nomeGruppo = nomeGruppo;
        this.numPersone = numPersone;
        this.idUtente = idUtente;
    }

    public int getIdScontrino() { return idScontrino; }
    public void setIdScontrino(int idScontrino) { this.idScontrino = idScontrino; }

    public LocalDate getDataAcquisto() { return dataAcquisto; }
    public void setDataAcquisto(LocalDate dataAcquisto) { this.dataAcquisto = dataAcquisto; }

    public String getNomeGruppo() { return nomeGruppo; }
    public void setNomeGruppo(String nomeGruppo) { this.nomeGruppo = nomeGruppo; }

    public Integer getNumPersone() { return numPersone; }
    public void setNumPersone(Integer numPersone) { this.numPersone = numPersone; }

    public int getIdUtente() { return idUtente; }
    public void setIdUtente(int idUtente) { this.idUtente = idUtente; }

    @Override
    public String toString() {
        return "Scontrino{idScontrino=" + idScontrino + ", dataAcquisto=" + dataAcquisto + "}";
    }
}