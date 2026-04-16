package it.unibo.zoo.model.entity;

public class StatoEsistenza {

    private int idStato;
    private String nome;
    private String descrizione;

    public StatoEsistenza() {}

    public StatoEsistenza(int idStato, String nome, String descrizione) {
        this.idStato = idStato;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public int getIdStato() { return idStato; }
    public void setIdStato(int idStato) { this.idStato = idStato; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    @Override
    public String toString() {
        return "StatoEsistenza{idStato=" + idStato + ", nome='" + nome + "'}";
    }
}