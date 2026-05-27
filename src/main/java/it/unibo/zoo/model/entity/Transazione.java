package it.unibo.zoo.model.entity;

import java.time.LocalDate;

public class Transazione {

    private int idTransazione;
    private String tipo;            // "E" = entrata, "U" = uscita
    private double importo;
    private String descrizione;
    private LocalDate data;
    private int idCategoria;        // FK → Categorie_Transazione

    public Transazione() {}

    public Transazione(int idTransazione, String tipo, double importo,
                       String descrizione, LocalDate data, int idCategoria) {
        this.idTransazione = idTransazione;
        this.tipo = tipo;
        this.importo = importo;
        this.descrizione = descrizione;
        this.data = data;
        this.idCategoria = idCategoria;
    }

    public int getIdTransazione() { return idTransazione; }
    public void setIdTransazione(int idTransazione) { this.idTransazione = idTransazione; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getImporto() { return importo; }
    public void setImporto(double importo) { this.importo = importo; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    @Override
    public String toString() {
        return "Transazione{idTransazione=" + idTransazione + ", tipo='" + tipo
                + "', importo=" + importo + "}";
    }
}
