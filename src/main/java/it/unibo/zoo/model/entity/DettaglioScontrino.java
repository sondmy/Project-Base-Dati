package it.unibo.zoo.model.entity;

public class DettaglioScontrino {

    private int idScontrino;    // FK → Scontrini
    private int idBiglietto;    // FK → Tipi_Biglietto
    private int quantita;
    private double prezzoUnitario;

    public DettaglioScontrino() {}

    public DettaglioScontrino(int idScontrino, int idBiglietto, int quantita, double prezzoUnitario) {
        this.idScontrino = idScontrino;
        this.idBiglietto = idBiglietto;
        this.quantita = quantita;
        this.prezzoUnitario = prezzoUnitario;
    }

    public int getIdScontrino() { return idScontrino; }
    public void setIdScontrino(int idScontrino) { this.idScontrino = idScontrino; }

    public int getIdBiglietto() { return idBiglietto; }
    public void setIdBiglietto(int idBiglietto) { this.idBiglietto = idBiglietto; }

    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }

    public double getPrezzoUnitario() { return prezzoUnitario; }
    public void setPrezzoUnitario(double prezzoUnitario) { this.prezzoUnitario = prezzoUnitario; }

    @Override
    public String toString() {
        return "DettaglioScontrino{idScontrino=" + idScontrino
                + ", idBiglietto=" + idBiglietto + ", quantita=" + quantita + "}";
    }
}
