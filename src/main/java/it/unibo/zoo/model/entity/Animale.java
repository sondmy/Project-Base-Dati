package it.unibo.zoo.model.entity;

import java.time.LocalDate;

public class Animale {

    private int idAnimale;
    private String nome;
    private char sesso;  // M/F
    private boolean attivo;
    private LocalDate dataNascita;  // nullable
    private LocalDate dataArrivo;  // nullable
    private LocalDate dataUscita;  // nullable
    private int idRecinto;  // FK
    private int idSpecie;  // FK

    public Animale() {}

    public Animale(int idAnimale, String nome, char sesso, boolean attivo, LocalDate dataNascita, LocalDate dataArrivo, LocalDate dataUscita, int idRecinto, int idSpecie) {
        this.idAnimale = idAnimale;
        this.nome = nome;
        this.sesso = sesso;
        this.attivo = attivo;
        this.dataNascita = dataNascita;
        this.dataArrivo = dataArrivo;
        this.dataUscita = dataUscita;
        this.idRecinto = idRecinto;
        this.idSpecie = idSpecie;
    }

    public int getIdAnimale() { return idAnimale; }
    public void setIdAnimale(int idAnimale) { this.idAnimale = idAnimale; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public char getSesso() { return sesso; }
    public void setSesso(char sesso) { this.sesso = sesso; }

    public boolean isAttivo() { return attivo; }
    public void setAttivo(boolean attivo) { this.attivo = attivo; }

    public LocalDate getDataNascita() { return dataNascita; }
    public void setDataNascita(LocalDate dataNascita) { this.dataNascita = dataNascita; }

    public LocalDate getDataArrivo() { return dataArrivo; }
    public void setDataArrivo(LocalDate dataArrivo) { this.dataArrivo = dataArrivo; }

    public LocalDate getDataUscita() { return dataUscita; }
    public void setDataUscita(LocalDate dataUscita) { this.dataUscita = dataUscita; }

    public int getIdRecinto() { return idRecinto; }
    public void setIdRecinto(int idRecinto) { this.idRecinto = idRecinto; }

    public int getIdSpecie() { return idSpecie; }
    public void setIdSpecie(int idSpecie) { this.idSpecie = idSpecie; }

    @Override
    public String toString() {
        return "Animale{idAnimale=" + idAnimale + ", nome=" + nome + ", sesso=" + sesso + ", attivo=" + attivo + "}";
    }
}