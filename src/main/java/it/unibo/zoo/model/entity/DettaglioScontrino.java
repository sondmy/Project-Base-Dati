package it.unibo.zoo.model.entity;

public class DettaglioScontrino {

    private int idDettaglio;
    private int idScontrino;  // FK
    private int idBiglietto;  // FK
    private int quantita;
    private double prezzoPagatoBiglietto;  // storico al momento acquisto

    public DettaglioScontrino() {}

    public DettaglioScontrino(int idDettaglio, int idScontrino, int idBiglietto, int quantita, double prezzoPagatoBiglietto) {
        this.idDettaglio = idDettaglio;
        this.idScontrino = idScontrino;
        this.idBiglietto = idBiglietto;
        this.quantita = quantita;
        this.prezzoPagatoBiglietto = prezzoPagatoBiglietto;
    }

    public DettaglioScontrino(int idScontrino, int idBiglietto, int quantita, double prezzoPagatoBiglietto) {
        this.idScontrino = idScontrino;
        this.idBiglietto = idBiglietto;
        this.quantita = quantita;
        this.prezzoPagatoBiglietto = prezzoPagatoBiglietto;
    }

    public int getIdDettaglio() { return idDettaglio; }
    public void setIdDettaglio(int idDettaglio) { this.idDettaglio = idDettaglio; }

    public int getIdScontrino() { return idScontrino; }
    public void setIdScontrino(int idScontrino) { this.idScontrino = idScontrino; }

    public int getIdBiglietto() { return idBiglietto; }
    public void setIdBiglietto(int idBiglietto) { this.idBiglietto = idBiglietto; }

    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }

    public double getPrezzoPagatoBiglietto() { return prezzoPagatoBiglietto; }
    public void setPrezzoPagatoBiglietto(double prezzoPagatoBiglietto) { this.prezzoPagatoBiglietto = prezzoPagatoBiglietto; }

    @Override
    public String toString() {
        return "DettaglioScontrino{idDettaglio=" + idDettaglio + ", idScontrino=" + idScontrino + ", idBiglietto=" + idBiglietto + ", quantita=" + quantita + "}";
    }
}