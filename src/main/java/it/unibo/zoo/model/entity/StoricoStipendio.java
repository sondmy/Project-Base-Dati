package it.unibo.zoo.model.entity;

import java.time.LocalDate;

public class StoricoStipendio {

    private int idStorico;
    private int idDipendente;       // FK → Dipendenti
    private double prezzoOrario;
    private LocalDate dataInizio;
    private LocalDate dataFine;     // nullable — null = contratto ancora attivo

    public StoricoStipendio() {}

    public StoricoStipendio(int idStorico, int idDipendente, double prezzoOrario,
                            LocalDate dataInizio, LocalDate dataFine) {
        this.idStorico = idStorico;
        this.idDipendente = idDipendente;
        this.prezzoOrario = prezzoOrario;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public int getIdStorico() { return idStorico; }
    public void setIdStorico(int idStorico) { this.idStorico = idStorico; }

    public int getIdDipendente() { return idDipendente; }
    public void setIdDipendente(int idDipendente) { this.idDipendente = idDipendente; }

    public double getPrezzoOrario() { return prezzoOrario; }
    public void setPrezzoOrario(double prezzoOrario) { this.prezzoOrario = prezzoOrario; }

    public LocalDate getDataInizio() { return dataInizio; }
    public void setDataInizio(LocalDate dataInizio) { this.dataInizio = dataInizio; }

    public LocalDate getDataFine() { return dataFine; }
    public void setDataFine(LocalDate dataFine) { this.dataFine = dataFine; }

    @Override
    public String toString() {
        return "StoricoStipendio{idStorico=" + idStorico + ", idDipendente=" + idDipendente
                + ", prezzoOrario=" + prezzoOrario + "}";
    }
}