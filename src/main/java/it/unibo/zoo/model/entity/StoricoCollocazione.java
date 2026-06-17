package it.unibo.zoo.model.entity;

import java.time.LocalDate;

public class StoricoCollocazione {

    private int idStorico;
    private int idAnimale;  // FK
    private int idRecinto;  // FK
    private LocalDate dataInizio;
    private LocalDate dataFine;  // nullable

    public StoricoCollocazione() {}

    public StoricoCollocazione(int idStorico, int idAnimale, int idRecinto, LocalDate dataInizio, LocalDate dataFine) {
        this.idStorico = idStorico;
        this.idAnimale = idAnimale;
        this.idRecinto = idRecinto;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public int getIdStorico() { return idStorico; }
    public void setIdStorico(int idStorico) { this.idStorico = idStorico; }

    public int getIdAnimale() { return idAnimale; }
    public void setIdAnimale(int idAnimale) { this.idAnimale = idAnimale; }

    public int getIdRecinto() { return idRecinto; }
    public void setIdRecinto(int idRecinto) { this.idRecinto = idRecinto; }

    public LocalDate getDataInizio() { return dataInizio; }
    public void setDataInizio(LocalDate dataInizio) { this.dataInizio = dataInizio; }

    public LocalDate getDataFine() { return dataFine; }
    public void setDataFine(LocalDate dataFine) { this.dataFine = dataFine; }

    @Override
    public String toString() {
        return "StoricoCollocazione{idStorico=" + idStorico + ", idAnimale=" + idAnimale + ", idRecinto=" + idRecinto + ", dataInizio=" + dataInizio + "}";
    }
}