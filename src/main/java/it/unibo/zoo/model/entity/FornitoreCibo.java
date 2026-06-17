package it.unibo.zoo.model.entity;

public class FornitoreCibo {

    private int idFornitore;  // PK FK
    private int idTipoCibo;  // PK FK

    public FornitoreCibo() {}

    public FornitoreCibo(int idFornitore, int idTipoCibo) {
        this.idFornitore = idFornitore;
        this.idTipoCibo = idTipoCibo;
    }

    public int getIdFornitore() { return idFornitore; }
    public void setIdFornitore(int idFornitore) { this.idFornitore = idFornitore; }

    public int getIdTipoCibo() { return idTipoCibo; }
    public void setIdTipoCibo(int idTipoCibo) { this.idTipoCibo = idTipoCibo; }

    @Override
    public String toString() {
        return "FornitoreCibo{idFornitore=" + idFornitore + ", idTipoCibo=" + idTipoCibo + "}";
    }
}