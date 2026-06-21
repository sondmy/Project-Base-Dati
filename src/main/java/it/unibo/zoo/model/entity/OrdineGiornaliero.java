package it.unibo.zoo.model.entity;

import java.time.LocalDate;

public class OrdineGiornaliero {

    private int idOrdine;
    private LocalDate data;
    private double quantitaKg;
    private boolean pagato;
    private int idFornitore;    // FK → Fornitori
    private int idTipoCibo;     // FK → Tipi_Cibo

    public OrdineGiornaliero() {}

    public OrdineGiornaliero(int idOrdine, LocalDate data, double quantitaKg,
                             boolean pagato, int idFornitore, int idTipoCibo) {
        this.idOrdine = idOrdine;
        this.data = data;
        this.quantitaKg = quantitaKg;
        this.pagato = pagato;
        this.idFornitore = idFornitore;
        this.idTipoCibo = idTipoCibo;
    }

    public OrdineGiornaliero(LocalDate data, double quantitaKg,
                             boolean pagato, int idFornitore, int idTipoCibo) {
        this.data = data;
        this.quantitaKg = quantitaKg;
        this.pagato = pagato;
        this.idFornitore = idFornitore;
        this.idTipoCibo = idTipoCibo;
    }

    public int getIdOrdine() { return idOrdine; }
    public void setIdOrdine(int idOrdine) { this.idOrdine = idOrdine; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public double getQuantitaKg() { return quantitaKg; }
    public void setQuantitaKg(double quantitaKg) { this.quantitaKg = quantitaKg; }

    public boolean isPagato() { return pagato; }
    public void setPagato(boolean pagato) { this.pagato = pagato; }

    public int getIdFornitore() { return idFornitore; }
    public void setIdFornitore(int idFornitore) { this.idFornitore = idFornitore; }

    public int getIdTipoCibo() { return idTipoCibo; }
    public void setIdTipoCibo(int idTipoCibo) { this.idTipoCibo = idTipoCibo; }

    @Override
    public String toString() {
        return "OrdineGiornaliero{idOrdine=" + idOrdine + ", data=" + data
                + ", quantitaKg=" + quantitaKg + ", pagato=" + pagato + "}";
    }
}
