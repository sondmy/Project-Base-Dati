package it.unibo.zoo.model.entity;

public class FamigliaSpecie {

    private int idFamigliaSpecie;
    private String nome;
    private String descrizione;  // nullable

    public FamigliaSpecie() {}

    public FamigliaSpecie(int idFamigliaSpecie, String nome, String descrizione) {
        this.idFamigliaSpecie = idFamigliaSpecie;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public FamigliaSpecie(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public int getIdFamigliaSpecie() { return idFamigliaSpecie; }
    public void setIdFamigliaSpecie(int idFamigliaSpecie) { this.idFamigliaSpecie = idFamigliaSpecie; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    @Override
    public String toString() {
        return "FamigliaSpecie{idFamigliaSpecie=" + idFamigliaSpecie + ", nome=" + nome + "}";
    }
}