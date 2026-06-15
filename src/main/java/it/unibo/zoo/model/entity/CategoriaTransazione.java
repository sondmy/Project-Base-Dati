package it.unibo.zoo.model.entity;

public class CategoriaTransazione {

    private int idCategoria;
    private String nome;
    private String descrizione;  // nullable
    private String tipo;  // ENTRATA|USCITA

    public CategoriaTransazione() {}

    public CategoriaTransazione(int idCategoria, String nome, String descrizione, String tipo) {
        this.idCategoria = idCategoria;
        this.nome = nome;
        this.descrizione = descrizione;
        this.tipo = tipo;
    }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    @Override
    public String toString() {
        return "CategoriaTransazione{idCategoria=" + idCategoria + ", nome=" + nome + ", tipo=" + tipo + "}";
    }
}