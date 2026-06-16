package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.StoricoStipendio;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StoricoStipendioDao extends AbstractCrudDao<StoricoStipendio> {

    @Override
    protected String tableName() {
        return "storico_stipendio";
    }

    @Override
    protected String idColumnName() { return "id_storico"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO storico_stipendio (id_dipendente, prezzo_orario, data_inizio, data_fine) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE storico_stipendio SET id_dipendente = ?, prezzo_orario = ?, data_inizio = ?, data_fine = ? WHERE id_storico = ?";
    }

    @Override
    protected int getId(StoricoStipendio entity) {
        return entity.getIdStorico();
    }

    @Override
    protected void setId(StoricoStipendio entity, int id) {
        entity.setIdStorico(id);
    }

    @Override
    protected StoricoStipendio mapRow(ResultSet resultSet) throws SQLException {
        return new StoricoStipendio(
                resultSet.getInt("id_storico"),
                resultSet.getInt("id_dipendente"),
                resultSet.getDouble("prezzo_orario"),
                JdbcUtils.getNullableDate(resultSet, "data_inizio"),
                JdbcUtils.getNullableDate(resultSet, "data_fine")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, StoricoStipendio entity) throws SQLException {
        int index = 1;
        statement.setInt(index++, entity.getIdDipendente());
        statement.setDouble(index++, entity.getPrezzoOrario());
        statement.setDate(index++, java.sql.Date.valueOf(entity.getDataInizio()));
        JdbcUtils.setNullableDate(statement, index++, entity.getDataFine());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, StoricoStipendio entity) throws SQLException {
        int index = 1;
        statement.setInt(index++, entity.getIdDipendente());
        statement.setDouble(index++, entity.getPrezzoOrario());
        statement.setDate(index++, java.sql.Date.valueOf(entity.getDataInizio()));
        JdbcUtils.setNullableDate(statement, index++, entity.getDataFine());
        statement.setInt(index++, entity.getIdStorico());
    }

    public List<StoricoStipendio> findByDipendente(int idDipendente) {
        return queryMany("SELECT * FROM storico_stipendio WHERE id_dipendente = ? ORDER BY data_inizio DESC", statement -> statement.setInt(1, idDipendente));
    }

}
