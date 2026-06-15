package it.unibo.zoo.model.entity;

import java.time.LocalDate;

public class Transazione {

    private int idTransazione;
    private String tipo;  // ENTRATA|USCITA
    private double importo;  // sempre positivo
    private LocalDate data;
    private String descrizione;  // nullable
    private int idCategoria;  // FK
    private int idUtente;  // FK
    private Integer idFornitore;  // nullable FK
    private Integer idScontrino;  // nullable FK

    public Transazione() {}

    public Transazione(int idTransazione, String tipo, double importo, LocalDate data, String descrizione, int idCategoria, int idUtente, Integer idFornitore, Integer idScontrino) {
        this.idTransazione = idTransazione;
        this.tipo = tipo;
        this.importo = importo;
        this.data = data;
        this.descrizione = descrizione;
        this.idCategoria = idCategoria;
        this.idUtente = idUtente;
        this.idFornitore = idFornitore;
        this.idScontrino = idScontrino;
    }

    public int getIdTransazione() { return idTransazione; }
    public void setIdTransazione(int idTransazione) { this.idTransazione = idTransazione; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getImporto() { return importo; }
    public void setImporto(double importo) { this.importo = importo; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public int getIdUtente() { return idUtente; }
    public void setIdUtente(int idUtente) { this.idUtente = idUtente; }

    public Integer getIdFornitore() { return idFornitore; }
    public void setIdFornitore(Integer idFornitore) { this.idFornitore = idFornitore; }

    public Integer getIdScontrino() { return idScontrino; }
    public void setIdScontrino(Integer idScontrino) { this.idScontrino = idScontrino; }

    @Override
    public String toString() {
        return "Transazione{idTransazione=" + idTransazione + ", tipo=" + tipo + ", importo=" + importo + ", data=" + data + "}";
    }
}