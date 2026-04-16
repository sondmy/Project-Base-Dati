package it.unibo.zoo.model.entity;

public class TipoAnimale {

    private int idTipo;
    private String nome;
    private String descrizione;

    public TipoAnimale() {}

    public TipoAnimale(int idTipo, String nome, String descrizione) {
        this.idTipo = idTipo;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public int getIdTipo() { return idTipo; }
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    @Override
    public String toString() {
        return "TipoAnimale{idTipo=" + idTipo + ", nome='" + nome + "'}";
    }
}