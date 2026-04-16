package it.unibo.zoo.model.entity;

import java.time.LocalDate;

public class Dipendente {

    private int idDipendente;
    private String codiceFiscale;   // UNIQUE NOT NULL
    private String nome;
    private String cognome;
    private LocalDate compleanno;   // nullable
    private int idMansione;         // FK → Tipi_Mansione
    private LocalDate dataAssunzione;
    private double prezzoOrario;

    public Dipendente() {}

    public Dipendente(int idDipendente, String codiceFiscale, String nome, String cognome,
                      LocalDate compleanno, int idMansione,
                      LocalDate dataAssunzione, double prezzoOrario) {
        this.idDipendente = idDipendente;
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.compleanno = compleanno;
        this.idMansione = idMansione;
        this.dataAssunzione = dataAssunzione;
        this.prezzoOrario = prezzoOrario;
    }

    public int getIdDipendente() { return idDipendente; }
    public void setIdDipendente(int idDipendente) { this.idDipendente = idDipendente; }

    public String getCodiceFiscale() { return codiceFiscale; }
    public void setCodiceFiscale(String codiceFiscale) { this.codiceFiscale = codiceFiscale; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public LocalDate getCompleanno() { return compleanno; }
    public void setCompleanno(LocalDate compleanno) { this.compleanno = compleanno; }

    public int getIdMansione() { return idMansione; }
    public void setIdMansione(int idMansione) { this.idMansione = idMansione; }

    public LocalDate getDataAssunzione() { return dataAssunzione; }
    public void setDataAssunzione(LocalDate dataAssunzione) { this.dataAssunzione = dataAssunzione; }

    public double getPrezzoOrario() { return prezzoOrario; }
    public void setPrezzoOrario(double prezzoOrario) { this.prezzoOrario = prezzoOrario; }

    @Override
    public String toString() {
        return "Dipendente{idDipendente=" + idDipendente + ", nome='" + nome + " " + cognome + "', cf='" + codiceFiscale + "'}";
    }
}