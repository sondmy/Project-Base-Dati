package it.unibo.zoo.model.entity;

public class Habitat {

    private int idHabitat;
    private String nome;
    private String descrizione;  // nullable

    public Habitat() {}

    public Habitat(int idHabitat, String nome, String descrizione) {
        this.idHabitat = idHabitat;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public int getIdHabitat() { return idHabitat; }
    public void setIdHabitat(int idHabitat) { this.idHabitat = idHabitat; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    @Override
    public String toString() {
        return "Habitat{idHabitat=" + idHabitat + ", nome=" + nome + "}";
    }
}