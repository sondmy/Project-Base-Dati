package it.unibo.zoo.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Turno {

    private int idTurno;
    private LocalDate dataGiorno;
    private LocalDateTime oraInizio;
    private LocalDateTime oraFine;
    private int idDipendente;  // FK
    private int idArea;  // FK

    public Turno() {}

    public Turno(int idTurno, LocalDate dataGiorno, LocalDateTime oraInizio, LocalDateTime oraFine, int idDipendente, int idArea) {
        this.idTurno = idTurno;
        this.dataGiorno = dataGiorno;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.idDipendente = idDipendente;
        this.idArea = idArea;
    }

    public Turno(LocalDate dataGiorno, LocalDateTime oraInizio, LocalDateTime oraFine, int idDipendente, int idArea) {
        this(0, dataGiorno, oraInizio, oraFine, idDipendente, idArea);
    }

    public Turno(int idTurno, LocalDateTime oraInizio, LocalDateTime oraFine, int idDipendente, int idArea) {
        this(idTurno, null, oraInizio, oraFine, idDipendente, idArea);
    }

    public Turno(LocalDateTime oraInizio, LocalDateTime oraFine, int idDipendente, int idArea) {
        this(0, null, oraInizio, oraFine, idDipendente, idArea);
    }

    public int getIdTurno() { return idTurno; }
    public void setIdTurno(int idTurno) { this.idTurno = idTurno; }

    public LocalDate getDataGiorno() {
        if (dataGiorno != null) {
            return dataGiorno;
        }
        return oraInizio != null ? oraInizio.toLocalDate() : null;
    }
    public void setDataGiorno(LocalDate dataGiorno) { this.dataGiorno = dataGiorno; }

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
        return "Turno{idTurno=" + idTurno + ", dataGiorno=" + getDataGiorno() + ", oraInizio=" + oraInizio + ", oraFine=" + oraFine + "}";
    }
}