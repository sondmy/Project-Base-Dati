package it.unibo.zoo.model.entity;

import java.time.LocalDate;

public class Dipendente {

    private int idDipendente;
    private String codiceFiscale;  // UNIQUE
    private String nome;
    private String cognome;
    private LocalDate dataNascita;  // nullable
    private LocalDate dataAssunzione;
    private double prezzoOrario;
    private int idMansione;  // FK

    public Dipendente() {}

    public Dipendente(int idDipendente, String codiceFiscale, String nome, String cognome, LocalDate dataNascita, LocalDate dataAssunzione, double prezzoOrario, int idMansione) {
        this.idDipendente = idDipendente;
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.dataAssunzione = dataAssunzione;
        this.prezzoOrario = prezzoOrario;
        this.idMansione = idMansione;
    }

    public int getIdDipendente() { return idDipendente; }
    public void setIdDipendente(int idDipendente) { this.idDipendente = idDipendente; }

    public String getCodiceFiscale() { return codiceFiscale; }
    public void setCodiceFiscale(String codiceFiscale) { this.codiceFiscale = codiceFiscale; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public LocalDate getDataNascita() { return dataNascita; }
    public void setDataNascita(LocalDate dataNascita) { this.dataNascita = dataNascita; }

    public LocalDate getDataAssunzione() { return dataAssunzione; }
    public void setDataAssunzione(LocalDate dataAssunzione) { this.dataAssunzione = dataAssunzione; }

    public double getPrezzoOrario() { return prezzoOrario; }
    public void setPrezzoOrario(double prezzoOrario) { this.prezzoOrario = prezzoOrario; }

    public int getIdMansione() { return idMansione; }
    public void setIdMansione(int idMansione) { this.idMansione = idMansione; }

    @Override
    public String toString() {
        return "Dipendente{idDipendente=" + idDipendente + ", codiceFiscale=" + codiceFiscale + ", nome=" + nome + ", cognome=" + cognome + "}";
    }
}