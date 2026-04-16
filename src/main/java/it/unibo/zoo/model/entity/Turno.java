package it.unibo.zoo.model.entity;

import java.time.LocalDateTime;

public class Turno {

    private int idTurno;
    private LocalDateTime oraInizio;
    private LocalDateTime oraFine;
    private int idDipendente;   // FK → Dipendenti
    private int idArea;         // FK → Aree

    public Turno() {}

    public Turno(int idTurno, LocalDateTime oraInizio, LocalDateTime oraFine,
                 int idDipendente, int idArea) {
        this.idTurno = idTurno;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.idDipendente = idDipendente;
        this.idArea = idArea;
    }

    public int getIdTurno() { return idTurno; }
    public void setIdTurno(int idTurno) { this.idTurno = idTurno; }

    public LocalDateTime getOraInizio() { return oraInizio; }
    public void setOraInizio(LocalDateTime oraInizio) { this.oraInizio = oraInizio; }

    public LocalDateTime getOraFine() { return oraFine; }
    public void setOraFine(LocalDateTime oraFine) { this.oraFine = oraFine; }

    public int getIdDipendente() { return idDipendente; }
    public void setIdDipendente(int idDipendente) { this.idDipendente = idDipendente; }

    public int getIdArea() { return idArea; }
    public void setIdArea(int idArea) { this.idArea = idArea; }

    @Override
    public String toString() {
        return "Turno{idTurno=" + idTurno + ", oraInizio=" + oraInizio + ", oraFine=" + oraFine + "}";
    }
}