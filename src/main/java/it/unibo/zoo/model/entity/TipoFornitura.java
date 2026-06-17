package it.unibo.zoo.model.entity;

public class TipoFornitura {

    private int idTipoFornitura;
    private String nome;
    private String descrizione;  // nullable

    public TipoFornitura() {}

    public TipoFornitura(int idTipoFornitura, String nome, String descrizione) {
        this.idTipoFornitura = idTipoFornitura;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public int getIdTipoFornitura() { return idTipoFornitura; }
    public void setIdTipoFornitura(int idTipoFornitura) { this.idTipoFornitura = idTipoFornitura; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    @Override
    public String toString() {
        return "TipoFornitura{idTipoFornitura=" + idTipoFornitura + ", nome=" + nome + "}";
    }
}