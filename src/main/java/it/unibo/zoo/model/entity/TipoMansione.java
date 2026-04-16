package it.unibo.zoo.model.entity;

public class TipoMansione {

    private int idTipoMansione;
    private String nome;
    private String descrizione;

    public TipoMansione() {}

    public TipoMansione(int idTipoMansione, String nome, String descrizione) {
        this.idTipoMansione = idTipoMansione;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public int getIdTipoMansione() { return idTipoMansione; }
    public void setIdTipoMansione(int idTipoMansione) { this.idTipoMansione = idTipoMansione; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    @Override
    public String toString() {
        return "TipoMansione{idTipoMansione=" + idTipoMansione + ", nome='" + nome + "'}";
    }
}