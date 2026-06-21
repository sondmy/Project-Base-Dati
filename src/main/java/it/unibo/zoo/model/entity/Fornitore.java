package it.unibo.zoo.model.entity;

public class Fornitore {

    private int idFornitore;
    private String nomeAzienda;
    private String descrizione;  // nullable
    private String indirizzo;  // nullable
    private String iban;  // nullable
    private Integer idTipoFornitura;  // nullable FK

    public Fornitore() {}

    public Fornitore(int idFornitore, String nomeAzienda, String descrizione, String indirizzo, String iban, Integer idTipoFornitura) {
        this.idFornitore = idFornitore;
        this.nomeAzienda = nomeAzienda;
        this.descrizione = descrizione;
        this.indirizzo = indirizzo;
        this.iban = iban;
        this.idTipoFornitura = idTipoFornitura;
    }

    public Fornitore(String nomeAzienda, String descrizione, String indirizzo, String iban, Integer idTipoFornitura) {
        this.nomeAzienda = nomeAzienda;
        this.descrizione = descrizione;
        this.indirizzo = indirizzo;
        this.iban = iban;
        this.idTipoFornitura = idTipoFornitura;
    }

    public int getIdFornitore() { return idFornitore; }
    public void setIdFornitore(int idFornitore) { this.idFornitore = idFornitore; }

    public String getNomeAzienda() { return nomeAzienda; }
    public void setNomeAzienda(String nomeAzienda) { this.nomeAzienda = nomeAzienda; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public String getIndirizzo() { return indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }

    public String getIban() { return iban; }
    public void setIban(String iban) { this.iban = iban; }

    public Integer getIdTipoFornitura() { return idTipoFornitura; }
    public void setIdTipoFornitura(Integer idTipoFornitura) { this.idTipoFornitura = idTipoFornitura; }

    @Override
    public String toString() {
        return "Fornitore{idFornitore=" + idFornitore + ", nomeAzienda=" + nomeAzienda + "}";
    }
}