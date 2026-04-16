package it.unibo.zoo.model.entity;

public class TipoBiglietto {

    private int idBiglietto;
    private String nome;
    private String descrizione;
    private double prezzo;
    private boolean attivo;

    public TipoBiglietto() {}

    public TipoBiglietto(int idBiglietto, String nome, String descrizione,
                         double prezzo, boolean attivo) {
        this.idBiglietto = idBiglietto;
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.attivo = attivo;
    }

    public int getIdBiglietto() { return idBiglietto; }
    public void setIdBiglietto(int idBiglietto) { this.idBiglietto = idBiglietto; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public double getPrezzo() { return prezzo; }
    public void setPrezzo(double prezzo) { this.prezzo = prezzo; }

    public boolean isAttivo() { return attivo; }
    public void setAttivo(boolean attivo) { this.attivo = attivo; }

    @Override
    public String toString() {
        return "TipoBiglietto{idBiglietto=" + idBiglietto + ", nome='" + nome
                + "', prezzo=" + prezzo + ", attivo=" + attivo + "}";
    }
}