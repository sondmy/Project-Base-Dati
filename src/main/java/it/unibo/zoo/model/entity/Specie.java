package it.unibo.zoo.model.entity;

public class Specie {

    private int idSpecie;
    private String nomeScentifico;
    private String nomeComune;
    private Integer idHabitat;  // nullable FK
    private Integer idStato;  // nullable FK
    private Integer idFamigliaSpecie;  // nullable FK

    public Specie() {}

    public Specie(int idSpecie, String nomeScentifico, String nomeComune, Integer idHabitat, Integer idStato, Integer idFamigliaSpecie) {
        this.idSpecie = idSpecie;
        this.nomeScentifico = nomeScentifico;
        this.nomeComune = nomeComune;
        this.idHabitat = idHabitat;
        this.idStato = idStato;
        this.idFamigliaSpecie = idFamigliaSpecie;
    }

    public Specie(String nomeScentifico, String nomeComune, Integer idHabitat, Integer idStato, Integer idFamigliaSpecie) {
        this.nomeScentifico = nomeScentifico;
        this.nomeComune = nomeComune;
        this.idHabitat = idHabitat;
        this.idStato = idStato;
        this.idFamigliaSpecie = idFamigliaSpecie;
    }

    public int getIdSpecie() { return idSpecie; }
    public void setIdSpecie(int idSpecie) { this.idSpecie = idSpecie; }

    public String getNomeScentifico() { return nomeScentifico; }
    public void setNomeScentifico(String nomeScentifico) { this.nomeScentifico = nomeScentifico; }

    public String getNomeComune() { return nomeComune; }
    public void setNomeComune(String nomeComune) { this.nomeComune = nomeComune; }

    public Integer getIdHabitat() { return idHabitat; }
    public void setIdHabitat(Integer idHabitat) { this.idHabitat = idHabitat; }

    public Integer getIdStato() { return idStato; }
    public void setIdStato(Integer idStato) { this.idStato = idStato; }

    public Integer getIdFamigliaSpecie() { return idFamigliaSpecie; }
    public void setIdFamigliaSpecie(Integer idFamigliaSpecie) { this.idFamigliaSpecie = idFamigliaSpecie; }

    @Override
    public String toString() {
        return "Specie{idSpecie=" + idSpecie + ", nomeScentifico=" + nomeScentifico + ", nomeComune=" + nomeComune + "}";
    }
}