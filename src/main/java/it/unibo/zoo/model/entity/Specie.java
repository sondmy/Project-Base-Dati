package it.unibo.zoo.model.entity;

public class Specie {

    private int idSpecie;
    private String nomeScentifico;
    private String nomeComune;
    private int idStatoEsistenza;   // FK → Stati_Esistenza
    private int idTipoAnimale;      // FK → Tipi_Animale
    private int idHabitat;          // FK → Habitat

    public Specie() {}

    public Specie(int idSpecie, String nomeScentifico, String nomeComune,
                  int idStatoEsistenza, int idTipoAnimale, int idHabitat) {
        this.idSpecie = idSpecie;
        this.nomeScentifico = nomeScentifico;
        this.nomeComune = nomeComune;
        this.idStatoEsistenza = idStatoEsistenza;
        this.idTipoAnimale = idTipoAnimale;
        this.idHabitat = idHabitat;
    }

    public int getIdSpecie() { return idSpecie; }
    public void setIdSpecie(int idSpecie) { this.idSpecie = idSpecie; }

    public String getNomeScentifico() { return nomeScentifico; }
    public void setNomeScentifico(String nomeScentifico) { this.nomeScentifico = nomeScentifico; }

    public String getNomeComune() { return nomeComune; }
    public void setNomeComune(String nomeComune) { this.nomeComune = nomeComune; }

    public int getIdStatoEsistenza() { return idStatoEsistenza; }
    public void setIdStatoEsistenza(int idStatoEsistenza) { this.idStatoEsistenza = idStatoEsistenza; }

    public int getIdTipoAnimale() { return idTipoAnimale; }
    public void setIdTipoAnimale(int idTipoAnimale) { this.idTipoAnimale = idTipoAnimale; }

    public int getIdHabitat() { return idHabitat; }
    public void setIdHabitat(int idHabitat) { this.idHabitat = idHabitat; }

    @Override
    public String toString() {
        return "Specie{idSpecie=" + idSpecie + ", nomeComune='" + nomeComune + "', nomeScentifico='" + nomeScentifico + "'}";
    }
}