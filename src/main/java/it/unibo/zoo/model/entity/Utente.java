package it.unibo.zoo.model.entity;

import java.time.LocalDate;

public class Utente {

    private int idUtente;
    private String email;           // UNIQUE NOT NULL
    private String passwordHash;    // hash bcrypt, mai la password in chiaro
    private LocalDate dataRegistrazione;
    private Integer idDipendente;   // FK → Dipendenti — nullable (Integer, non int)

    public Utente() {}

    public Utente(int idUtente, String email, String passwordHash,
                  LocalDate dataRegistrazione, Integer idDipendente) {
        this.idUtente = idUtente;
        this.email = email;
        this.passwordHash = passwordHash;
        this.dataRegistrazione = dataRegistrazione;
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

    public Integer getIdDipendente() { return idDipendente; }
    public void setIdDipendente(Integer idDipendente) { this.idDipendente = idDipendente; }

    /** Restituisce true se l'utente è collegato a un dipendente. */
    public boolean isDipendente() { return idDipendente != null; }

    @Override
    public String toString() {
        return "Utente{idUtente=" + idUtente + ", email='" + email + "'}";
    }
}