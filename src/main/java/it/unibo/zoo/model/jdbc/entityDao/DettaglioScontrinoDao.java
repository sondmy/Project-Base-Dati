package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.DettaglioScontrino;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DettaglioScontrinoDao extends AbstractCrudDao<DettaglioScontrino> {

    @Override
    protected String tableName() {
        return "dettaglio_scontrino";
    }

    @Override
    protected String idColumnName() { return "id_dettaglio"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO dettaglio_scontrino (id_scontrino, id_biglietto, quantita, prezzo_pagato_biglietto) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE dettaglio_scontrino SET id_scontrino = ?, id_biglietto = ?, quantita = ?, prezzo_pagato_biglietto = ? WHERE id_dettaglio = ?";
    }

    @Override
    protected int getId(DettaglioScontrino entity) {
        return entity.getIdDettaglio();
    }

    @Override
    protected void setId(DettaglioScontrino entity, int id) {
        entity.setIdDettaglio(id);
    }

    @Override
    protected DettaglioScontrino mapRow(ResultSet resultSet) throws SQLException {
        return new DettaglioScontrino(
                resultSet.getInt("id_dettaglio"),
                resultSet.getInt("id_scontrino"),
                resultSet.getInt("id_biglietto"),
                resultSet.getInt("quantita"),
                resultSet.getDouble("prezzo_pagato_biglietto")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, DettaglioScontrino entity) throws SQLException {
        int index = 1;
        statement.setInt(index++, entity.getIdScontrino());
        statement.setInt(index++, entity.getIdBiglietto());
        statement.setInt(index++, entity.getQuantita());
        statement.setDouble(index++, entity.getPrezzoPagatoBiglietto());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, DettaglioScontrino entity) throws SQLException {
        int index = 1;
        statement.setInt(index++, entity.getIdScontrino());
        statement.setInt(index++, entity.getIdBiglietto());
        statement.setInt(index++, entity.getQuantita());
        statement.setDouble(index++, entity.getPrezzoPagatoBiglietto());
        statement.setInt(index++, entity.getIdDettaglio());
    }

    public List<DettaglioScontrino> findByScontrino(int idScontrino) {
        return queryMany("SELECT * FROM dettaglio_scontrino WHERE id_scontrino = ?", statement -> statement.setInt(1, idScontrino));
    }

    public List<DettaglioScontrino> findByBiglietto(int idBiglietto) {
        return queryMany("SELECT * FROM dettaglio_scontrino WHERE id_biglietto = ?", statement -> statement.setInt(1, idBiglietto));
    }

}
