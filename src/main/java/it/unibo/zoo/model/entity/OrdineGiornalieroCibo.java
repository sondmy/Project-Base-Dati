package it.unibo.zoo.model.entity;

import java.time.LocalDate;

public class OrdineGiornalieroCibo {

    private int idOrdine;
    private LocalDate dataOrdine;
    private double quantitaKg;
    private int idFornitore;  // FK
    private int idTipoCibo;  // FK
    private Integer idTransazione;  // nullable FK

    public OrdineGiornalieroCibo() {}

    public OrdineGiornalieroCibo(int idOrdine, LocalDate dataOrdine, double quantitaKg, int idFornitore, int idTipoCibo, Integer idTransazione) {
        this.idOrdine = idOrdine;
        this.dataOrdine = dataOrdine;
        this.quantitaKg = quantitaKg;
        this.idFornitore = idFornitore;
        this.idTipoCibo = idTipoCibo;
        this.idTransazione = idTransazione;
    }

    public OrdineGiornalieroCibo(LocalDate dataOrdine, double quantitaKg, int idFornitore, int idTipoCibo, Integer idTransazione) {
        this.dataOrdine = dataOrdine;
        this.quantitaKg = quantitaKg;
        this.idFornitore = idFornitore;
        this.idTipoCibo = idTipoCibo;
        this.idTransazione = idTransazione;
    }

    public int getIdOrdine() { return idOrdine; }
    public void setIdOrdine(int idOrdine) { this.idOrdine = idOrdine; }

    public LocalDate getDataOrdine() { return dataOrdine; }
    public void setDataOrdine(LocalDate dataOrdine) { this.dataOrdine = dataOrdine; }

    public double getQuantitaKg() { return quantitaKg; }
    public void setQuantitaKg(double quantitaKg) { this.quantitaKg = quantitaKg; }

    public int getIdFornitore() { return idFornitore; }
    public void setIdFornitore(int idFornitore) { this.idFornitore = idFornitore; }

    public int getIdTipoCibo() { return idTipoCibo; }
    public void setIdTipoCibo(int idTipoCibo) { this.idTipoCibo = idTipoCibo; }

    public Integer getIdTransazione() { return idTransazione; }
    public void setIdTransazione(Integer idTransazione) { this.idTransazione = idTransazione; }

    @Override
    public String toString() {
        return "OrdineGiornalieroCibo{idOrdine=" + idOrdine + ", dataOrdine=" + dataOrdine + ", idFornitore=" + idFornitore + ", idTipoCibo=" + idTipoCibo + "}";
    }
}