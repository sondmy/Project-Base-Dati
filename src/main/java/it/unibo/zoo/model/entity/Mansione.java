package it.unibo.zoo.model.entity;

public class Mansione {

    private int idMansione;
    private String nome;
    private String descrizione;  // nullable

    public Mansione() {}

    public Mansione(int idMansione, String nome, String descrizione) {
        this.idMansione = idMansione;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public int getIdMansione() { return idMansione; }
    public void setIdMansione(int idMansione) { this.idMansione = idMansione; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    @Override
    public String toString() {
        return "Mansione{idMansione=" + idMansione + ", nome=" + nome + "}";
    }
}