package it.unibo.zoo.model.entity;

import java.time.LocalDate;

public class VisitaMedica {

    private int idVisita;
    private Double peso;                // nullable — storico peso all'atto della visita
    private String diagnosi;
    private String noteTrattamento;
    private LocalDate dataVisita;
    private LocalDate dataFine;         // nullable
    private int idAnimale;              // FK → Animali
    private int idVeterinario;          // FK → Dipendenti

    public VisitaMedica() {}

    public VisitaMedica(int idVisita, Double peso, String diagnosi, String noteTrattamento,
                        LocalDate dataVisita, LocalDate dataFine,
                        int idAnimale, int idVeterinario) {
        this.idVisita = idVisita;
        this.peso = peso;
        this.diagnosi = diagnosi;
        this.noteTrattamento = noteTrattamento;
        this.dataVisita = dataVisita;
        this.dataFine = dataFine;
        this.idAnimale = idAnimale;
        this.idVeterinario = idVeterinario;
    }

    public int getIdVisita() { return idVisita; }
    public void setIdVisita(int idVisita) { this.idVisita = idVisita; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }

    public String getDiagnosi() { return diagnosi; }
    public void setDiagnosi(String diagnosi) { this.diagnosi = diagnosi; }

    public String getNoteTrattamento() { return noteTrattamento; }
    public void setNoteTrattamento(String noteTrattamento) { this.noteTrattamento = noteTrattamento; }

    public LocalDate getDataVisita() { return dataVisita; }
    public void setDataVisita(LocalDate dataVisita) { this.dataVisita = dataVisita; }

    public LocalDate getDataFine() { return dataFine; }
    public void setDataFine(LocalDate dataFine) { this.dataFine = dataFine; }

    public int getIdAnimale() { return idAnimale; }
    public void setIdAnimale(int idAnimale) { this.idAnimale = idAnimale; }

    public int getIdVeterinario() { return idVeterinario; }
    public void setIdVeterinario(int idVeterinario) { this.idVeterinario = idVeterinario; }

    @Override
    public String toString() {
        return "VisitaMedica{idVisita=" + idVisita + ", idAnimale=" + idAnimale
                + ", dataVisita=" + dataVisita + "}";
    }
}