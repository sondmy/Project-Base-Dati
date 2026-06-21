package it.unibo.zoo.model.entity;

public class FornitoreCibo {

    private int idFornitore;  // PK FK
    private int idTipoCibo;  // PK FK
    private double prezzo;

    public FornitoreCibo() {
        this.prezzo = 0.0;
    }

    public FornitoreCibo(int idFornitore, int idTipoCibo, double prezzo) {
        this.idFornitore = idFornitore;
        this.idTipoCibo = idTipoCibo;
        this.prezzo = prezzo;
    }

    public FornitoreCibo(int idFornitore, int idTipoCibo) {
        this(idFornitore, idTipoCibo, 0.0);
    }

    public int getIdFornitore() { return idFornitore; }
    public void setIdFornitore(int idFornitore) { this.idFornitore = idFornitore; }

    public int getIdTipoCibo() { return idTipoCibo; }
    public void setIdTipoCibo(int idTipoCibo) { this.idTipoCibo = idTipoCibo; }

    public double getPrezzo() { return prezzo; }
    public void setPrezzo(double prezzo) { this.prezzo = prezzo; }

    @Override
    public String toString() {
        return "FornitoreCibo{idFornitore=" + idFornitore + ", idTipoCibo=" + idTipoCibo + ", prezzo=" + prezzo + "}";
    }
}