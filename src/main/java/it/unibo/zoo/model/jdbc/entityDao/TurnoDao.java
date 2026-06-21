package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.Turno;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TurnoDao extends AbstractCrudDao<Turno> {

    @Override
    protected String tableName() {
        return "turno";
    }

    @Override
    protected String idColumnName() { return "id_turno"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO turno (data_giorno, ora_inizio, ora_fine, id_dipendente, id_area) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE turno SET data_giorno = ?, ora_inizio = ?, ora_fine = ?, id_dipendente = ?, id_area = ? WHERE id_turno = ?";
    }

    @Override
    protected int getId(Turno entity) {
        return entity.getIdTurno();
    }

    @Override
    protected void setId(Turno entity, int id) {
        entity.setIdTurno(id);
    }

    @Override
    protected Turno mapRow(ResultSet resultSet) throws SQLException {
        return new Turno(
                resultSet.getInt("id_turno"),
                JdbcUtils.getNullableDate(resultSet, "data_giorno"),
                JdbcUtils.getNullableDateTime(resultSet, "ora_inizio"),
                JdbcUtils.getNullableDateTime(resultSet, "ora_fine"),
                resultSet.getInt("id_dipendente"),
                resultSet.getInt("id_area")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, Turno entity) throws SQLException {
        int index = 1;
        statement.setDate(index++, java.sql.Date.valueOf(entity.getDataGiorno()));
        statement.setTimestamp(index++, java.sql.Timestamp.valueOf(entity.getOraInizio()));
        statement.setTimestamp(index++, java.sql.Timestamp.valueOf(entity.getOraFine()));
        statement.setInt(index++, entity.getIdDipendente());
        statement.setInt(index++, entity.getIdArea());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, Turno entity) throws SQLException {
        int index = 1;
        statement.setDate(index++, java.sql.Date.valueOf(entity.getDataGiorno()));
        statement.setTimestamp(index++, java.sql.Timestamp.valueOf(entity.getOraInizio()));
        statement.setTimestamp(index++, java.sql.Timestamp.valueOf(entity.getOraFine()));
        statement.setInt(index++, entity.getIdDipendente());
        statement.setInt(index++, entity.getIdArea());
        statement.setInt(index++, entity.getIdTurno());
    }

    public List<Turno> findByDipendente(int idDipendente) {
        return queryMany("SELECT * FROM turno WHERE id_dipendente = ? ORDER BY ora_inizio", statement -> statement.setInt(1, idDipendente));
    }

    public List<Turno> findByArea(int idArea) {
        return queryMany("SELECT * FROM turno WHERE id_area = ? ORDER BY ora_inizio", statement -> statement.setInt(1, idArea));
    }

    public List<Turno> findByData(LocalDate data) {
        return queryMany("SELECT * FROM turno WHERE ora_inizio::date = ? ORDER BY ora_inizio", statement -> statement.setDate(1, java.sql.Date.valueOf(data)));
    }

}
