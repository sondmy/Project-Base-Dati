package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.Scontrino;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ScontrinoDao extends AbstractCrudDao<Scontrino> {

    @Override
    protected String tableName() {
        return "scontrino";
    }

    @Override
    protected String idColumnName() { return "id_scontrino"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO scontrino (data_acquisto, nome_gruppo, num_persone, id_utente) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE scontrino SET data_acquisto = ?, nome_gruppo = ?, num_persone = ?, id_utente = ? WHERE id_scontrino = ?";
    }

    @Override
    protected int getId(Scontrino entity) {
        return entity.getIdScontrino();
    }

    @Override
    protected void setId(Scontrino entity, int id) {
        entity.setIdScontrino(id);
    }

    @Override
    protected Scontrino mapRow(ResultSet resultSet) throws SQLException {
        return new Scontrino(
                resultSet.getInt("id_scontrino"),
                JdbcUtils.getNullableDate(resultSet, "data_acquisto"),
                resultSet.getString("nome_gruppo"),
                JdbcUtils.getNullableInteger(resultSet, "num_persone"),
                resultSet.getInt("id_utente")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, Scontrino entity) throws SQLException {
        int index = 1;
        statement.setDate(index++, java.sql.Date.valueOf(entity.getDataAcquisto()));
        JdbcUtils.setNullableString(statement, index++, entity.getNomeGruppo());
        JdbcUtils.setNullableInteger(statement, index++, entity.getNumPersone());
        statement.setInt(index++, entity.getIdUtente());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, Scontrino entity) throws SQLException {
        int index = 1;
        statement.setDate(index++, java.sql.Date.valueOf(entity.getDataAcquisto()));
        JdbcUtils.setNullableString(statement, index++, entity.getNomeGruppo());
        JdbcUtils.setNullableInteger(statement, index++, entity.getNumPersone());
        statement.setInt(index++, entity.getIdUtente());
        statement.setInt(index++, entity.getIdScontrino());
    }

    public List<Scontrino> findByUtente(int idUtente) {
        return queryMany("SELECT * FROM scontrino WHERE id_utente = ? ORDER BY data_acquisto DESC", statement -> statement.setInt(1, idUtente));
    }

    public List<Scontrino> findByDataAcquisto(LocalDate dataAcquisto) {
        return queryMany("SELECT * FROM scontrino WHERE data_acquisto = ? ORDER BY id_scontrino DESC", statement -> statement.setDate(1, java.sql.Date.valueOf(dataAcquisto)));
    }

}
