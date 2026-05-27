package it.unibo.zoo.model.entity;

public class Fornitore {

    private int idFornitore;
    private String nome;
    private String telefono;
    private String email;

    public Fornitore() {}

    public Fornitore(int idFornitore, String nome, String telefono, String email) {
        this.idFornitore = idFornitore;
        this.nome = nome;
        this.telefono = telefono;
        this.email = email;
    }

    public int getIdFornitore() { return idFornitore; }
    public void setIdFornitore(int idFornitore) { this.idFornitore = idFornitore; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Fornitore{idFornitore=" + idFornitore + ", nome='" + nome + "'}";
    }
}
