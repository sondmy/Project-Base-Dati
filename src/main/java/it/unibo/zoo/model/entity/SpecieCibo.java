package it.unibo.zoo.model.entity;

/**
 * Rappresenta la tabella ponte _Specie (M:N tra Specie e Tipi_Cibo).
 * PK composta: (idSpecie, idTipoCibo).
 */
public class SpecieCibo {

    private int idSpecie;       // FK → Specie
    private int idTipoCibo;     // FK → Tipi_Cibo
    private double quantitaKgGiorno;

    public SpecieCibo() {}

    public SpecieCibo(int idSpecie, int idTipoCibo, double quantitaKgGiorno) {
        this.idSpecie = idSpecie;
        this.idTipoCibo = idTipoCibo;
        this.quantitaKgGiorno = quantitaKgGiorno;
    }

    public int getIdSpecie() { return idSpecie; }
    public void setIdSpecie(int idSpecie) { this.idSpecie = idSpecie; }

    public int getIdTipoCibo() { return idTipoCibo; }
    public void setIdTipoCibo(int idTipoCibo) { this.idTipoCibo = idTipoCibo; }

    public double getQuantitaKgGiorno() { return quantitaKgGiorno; }
    public void setQuantitaKgGiorno(double quantitaKgGiorno) { this.quantitaKgGiorno = quantitaKgGiorno; }

    @Override
    public String toString() {
        return "SpecieCibo{idSpecie=" + idSpecie + ", idTipoCibo=" + idTipoCibo
                + ", quantitaKgGiorno=" + quantitaKgGiorno + "}";
    }
}