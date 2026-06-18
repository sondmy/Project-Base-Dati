package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.VisitaMedica;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class VisitaMedicaDao extends AbstractCrudDao<VisitaMedica> {

    @Override
    protected String tableName() {
        return "visita_medica";
    }

    @Override
    protected String idColumnName() { return "id_visita"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO visita_medica (peso, diagnosi, note_trattamento, data_visita, data_fine, id_animale, id_veterinario) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE visita_medica SET peso = ?, diagnosi = ?, note_trattamento = ?, data_visita = ?, data_fine = ?, id_animale = ?, id_veterinario = ? WHERE id_visita = ?";
    }

    @Override
    protected int getId(VisitaMedica entity) {
        return entity.getIdVisita();
    }

    @Override
    protected void setId(VisitaMedica entity, int id) {
        entity.setIdVisita(id);
    }

    @Override
    protected VisitaMedica mapRow(ResultSet resultSet) throws SQLException {
        return new VisitaMedica(
                resultSet.getInt("id_visita"),
                JdbcUtils.getNullableDouble(resultSet, "peso"),
                resultSet.getString("diagnosi"),
                resultSet.getString("note_trattamento"),
                JdbcUtils.getNullableDate(resultSet, "data_visita"),
                JdbcUtils.getNullableDate(resultSet, "data_fine"),
                resultSet.getInt("id_animale"),
                resultSet.getInt("id_veterinario")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, VisitaMedica entity) throws SQLException {
        int index = 1;
        JdbcUtils.setNullableDouble(statement, index++, entity.getPeso());
        JdbcUtils.setNullableString(statement, index++, entity.getDiagnosi());
        JdbcUtils.setNullableString(statement, index++, entity.getNoteTrattamento());
        statement.setDate(index++, java.sql.Date.valueOf(entity.getDataVisita()));
        JdbcUtils.setNullableDate(statement, index++, entity.getDataFine());
        statement.setInt(index++, entity.getIdAnimale());
        statement.setInt(index++, entity.getIdDipendente());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, VisitaMedica entity) throws SQLException {
        int index = 1;
        JdbcUtils.setNullableDouble(statement, index++, entity.getPeso());
        JdbcUtils.setNullableString(statement, index++, entity.getDiagnosi());
        JdbcUtils.setNullableString(statement, index++, entity.getNoteTrattamento());
        statement.setDate(index++, java.sql.Date.valueOf(entity.getDataVisita()));
        JdbcUtils.setNullableDate(statement, index++, entity.getDataFine());
        statement.setInt(index++, entity.getIdAnimale());
        statement.setInt(index++, entity.getIdDipendente());
        statement.setInt(index++, entity.getIdVisita());
    }

    public List<VisitaMedica> findByAnimale(int idAnimale) {
        return queryMany("SELECT * FROM visita_medica WHERE id_animale = ? ORDER BY data_visita DESC", statement -> statement.setInt(1, idAnimale));
    }

    public List<VisitaMedica> findByDipendente(int idDipendente) {
        return queryMany("SELECT * FROM visita_medica WHERE id_veterinario = ? ORDER BY data_visita DESC", statement -> statement.setInt(1, idDipendente));
    }

    public List<VisitaMedica> findByDataVisita(LocalDate dataVisita) {
        return queryMany("SELECT * FROM visita_medica WHERE data_visita = ? ORDER BY id_visita DESC", statement -> statement.setDate(1, java.sql.Date.valueOf(dataVisita)));
    }

}
