package it.unibo.zoo.model.entity;

import java.time.LocalDate;

public class Animale {

    private int idAnimale;
    private String nome;
    private boolean vivo;
    private LocalDate dataNascita;  // nullable
    private LocalDate dataArrivo;   // nullable
    private LocalDate dataUscita;   // nullable — morte o trasferimento
    private int idSpecie;           // FK → Specie
    private int idRecinto;          // FK → Recinti

    public Animale() {}

    public Animale(int idAnimale, String nome, boolean vivo,
                   LocalDate dataNascita, LocalDate dataArrivo, LocalDate dataUscita,
                   int idSpecie, int idRecinto) {
        this.idAnimale = idAnimale;
        this.nome = nome;
        this.vivo = vivo;
        this.dataNascita = dataNascita;
        this.dataArrivo = dataArrivo;
        this.dataUscita = dataUscita;
        this.idSpecie = idSpecie;
        this.idRecinto = idRecinto;
    }

    public int getIdAnimale() { return idAnimale; }
    public void setIdAnimale(int idAnimale) { this.idAnimale = idAnimale; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public boolean isVivo() { return vivo; }
    public void setVivo(boolean vivo) { this.vivo = vivo; }

    public LocalDate getDataNascita() { return dataNascita; }
    public void setDataNascita(LocalDate dataNascita) { this.dataNascita = dataNascita; }

    public LocalDate getDataArrivo() { return dataArrivo; }
    public void setDataArrivo(LocalDate dataArrivo) { this.dataArrivo = dataArrivo; }

    public LocalDate getDataUscita() { return dataUscita; }
    public void setDataUscita(LocalDate dataUscita) { this.dataUscita = dataUscita; }

    public int getIdSpecie() { return idSpecie; }
    public void setIdSpecie(int idSpecie) { this.idSpecie = idSpecie; }

    public int getIdRecinto() { return idRecinto; }
    public void setIdRecinto(int idRecinto) { this.idRecinto = idRecinto; }

    @Override
    public String toString() {
        return "Animale{idAnimale=" + idAnimale + ", nome='" + nome + "', vivo=" + vivo + "}";
    }
}