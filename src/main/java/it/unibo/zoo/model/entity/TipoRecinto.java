package it.unibo.zoo.model.entity;

public class TipoRecinto {

    private int idTipoRecinto;
    private String nome;
    private String descrizione;  // nullable

    public TipoRecinto() {}

    public TipoRecinto(int idTipoRecinto, String nome, String descrizione) {
        this.idTipoRecinto = idTipoRecinto;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public TipoRecinto(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public int getIdTipoRecinto() { return idTipoRecinto; }
    public void setIdTipoRecinto(int idTipoRecinto) { this.idTipoRecinto = idTipoRecinto; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    @Override
    public String toString() {
        return "TipoRecinto{idTipoRecinto=" + idTipoRecinto + ", nome=" + nome + "}";
    }
}