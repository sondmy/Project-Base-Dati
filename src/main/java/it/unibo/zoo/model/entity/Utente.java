package it.unibo.zoo.model.entity;

import java.time.LocalDate;

public class Utente {

    private int idUtente;
    private String email;  // UNIQUE
    private String passwordHash;  // bcrypt — mai in chiaro
    private LocalDate dataRegistrazione;
    private String ruolo;
    private Integer idDipendente;  // nullable FK

    public Utente() {}

    public Utente(int idUtente, String email, String passwordHash, LocalDate dataRegistrazione, String ruolo, Integer idDipendente) {
        this.idUtente = idUtente;
        this.email = email;
        this.passwordHash = passwordHash;
        this.dataRegistrazione = dataRegistrazione;
        this.ruolo = ruolo;
        this.idDipendente = idDipendente;
    }
    
    public Utente(String email, String passwordHash, LocalDate dataRegistrazione, String ruolo, Integer idDipendente) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.dataRegistrazione = dataRegistrazione;
        this.ruolo = ruolo;
        this.idDipendente = idDipendente;
    }

    public int getIdUtente() { return idUtente; }
    public void setIdUtente(int idUtente) { this.idUtente = idUtente; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public LocalDate getDataRegistrazione() { return dataRegistrazione; }
    public void setDataRegistrazione(LocalDate dataRegistrazione) { this.dataRegistrazione = dataRegistrazione; }

    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }

    public Integer getIdDipendente() { return idDipendente; }
    public void setIdDipendente(Integer idDipendente) { this.idDipendente = idDipendente; }

    @Override
    public String toString() {
        return "Utente{idUtente=" + idUtente + ", email=" + email + ", ruolo=" + ruolo + "}";
    }
}