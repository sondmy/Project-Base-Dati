package it.unibo.zoo.model.entity;

public class TipoCibo {

    private int idTipoCibo;
    private String nome;
    private String descrizione;  // nullable

    public TipoCibo() {}

    public TipoCibo(int idTipoCibo, String nome, String descrizione) {
        this.idTipoCibo = idTipoCibo;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public TipoCibo(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public int getIdTipoCibo() { return idTipoCibo; }
    public void setIdTipoCibo(int idTipoCibo) { this.idTipoCibo = idTipoCibo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    @Override
    public String toString() {
        return "TipoCibo{idTipoCibo=" + idTipoCibo + ", nome=" + nome + "}";
    }
}