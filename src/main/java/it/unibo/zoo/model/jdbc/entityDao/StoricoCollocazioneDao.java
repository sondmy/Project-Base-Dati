package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.StoricoCollocazione;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StoricoCollocazioneDao extends AbstractCrudDao<StoricoCollocazione> {

    @Override
    protected String tableName() {
        return "storico_collocazione";
    }

    @Override
    protected String idColumnName() { return "id_storico"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO storico_collocazione (id_animale, id_recinto, data_inizio, data_fine) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE storico_collocazione SET id_animale = ?, id_recinto = ?, data_inizio = ?, data_fine = ? WHERE id_storico = ?";
    }

    @Override
    protected int getId(StoricoCollocazione entity) {
        return entity.getIdStorico();
    }

    @Override
    protected void setId(StoricoCollocazione entity, int id) {
        entity.setIdStorico(id);
    }

    @Override
    protected StoricoCollocazione mapRow(ResultSet resultSet) throws SQLException {
        return new StoricoCollocazione(
                resultSet.getInt("id_storico"),
                resultSet.getInt("id_animale"),
                resultSet.getInt("id_recinto"),
                JdbcUtils.getNullableDate(resultSet, "data_inizio"),
                JdbcUtils.getNullableDate(resultSet, "data_fine")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, StoricoCollocazione entity) throws SQLException {
        int index = 1;
        statement.setInt(index++, entity.getIdAnimale());
        statement.setInt(index++, entity.getIdRecinto());
        statement.setDate(index++, java.sql.Date.valueOf(entity.getDataInizio()));
        JdbcUtils.setNullableDate(statement, index++, entity.getDataFine());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, StoricoCollocazione entity) throws SQLException {
        int index = 1;
        statement.setInt(index++, entity.getIdAnimale());
        statement.setInt(index++, entity.getIdRecinto());
        statement.setDate(index++, java.sql.Date.valueOf(entity.getDataInizio()));
        JdbcUtils.setNullableDate(statement, index++, entity.getDataFine());
        statement.setInt(index++, entity.getIdStorico());
    }

    public List<StoricoCollocazione> findByAnimale(int idAnimale) {
        return queryMany("SELECT * FROM storico_collocazione WHERE id_animale = ? ORDER BY data_inizio DESC", statement -> statement.setInt(1, idAnimale));
    }

    public List<StoricoCollocazione> findByRecinto(int idRecinto) {
        return queryMany("SELECT * FROM storico_collocazione WHERE id_recinto = ? ORDER BY data_inizio DESC", statement -> statement.setInt(1, idRecinto));
    }

}
