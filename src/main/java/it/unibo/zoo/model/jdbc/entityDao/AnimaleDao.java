package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.Animale;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AnimaleDao extends AbstractCrudDao<Animale> {

    @Override
    protected String tableName() {
        return "animale";
    }

    @Override
    protected String idColumnName() { return "id_animale"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO animale (nome, sesso, attivo, data_nascita, data_arrivo, data_uscita, id_recinto, id_specie) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE animale SET nome = ?, sesso = ?, attivo = ?, data_nascita = ?, data_arrivo = ?, data_uscita = ?, id_recinto = ?, id_specie = ? WHERE id_animale = ?";
    }

    @Override
    protected int getId(Animale entity) {
        return entity.getIdAnimale();
    }

    @Override
    protected void setId(Animale entity, int id) {
        entity.setIdAnimale(id);
    }

    @Override
    protected Animale mapRow(ResultSet resultSet) throws SQLException {
        return new Animale(
                resultSet.getInt("id_animale"),
                resultSet.getString("nome"),
                resultSet.getString("sesso").charAt(0),
                resultSet.getBoolean("attivo"),
                JdbcUtils.getNullableDate(resultSet, "data_nascita"),
                JdbcUtils.getNullableDate(resultSet, "data_arrivo"),
                JdbcUtils.getNullableDate(resultSet, "data_uscita"),
                resultSet.getInt("id_recinto"),
                resultSet.getInt("id_specie")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, Animale entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        statement.setString(index++, String.valueOf(entity.getSesso()));
        statement.setBoolean(index++, entity.isAttivo());
        JdbcUtils.setNullableDate(statement, index++, entity.getDataNascita());
        JdbcUtils.setNullableDate(statement, index++, entity.getDataArrivo());
        JdbcUtils.setNullableDate(statement, index++, entity.getDataUscita());
        statement.setInt(index++, entity.getIdRecinto());
        statement.setInt(index++, entity.getIdSpecie());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, Animale entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        statement.setString(index++, String.valueOf(entity.getSesso()));
        statement.setBoolean(index++, entity.isAttivo());
        JdbcUtils.setNullableDate(statement, index++, entity.getDataNascita());
        JdbcUtils.setNullableDate(statement, index++, entity.getDataArrivo());
        JdbcUtils.setNullableDate(statement, index++, entity.getDataUscita());
        statement.setInt(index++, entity.getIdRecinto());
        statement.setInt(index++, entity.getIdSpecie());
        statement.setInt(index++, entity.getIdAnimale());
    }

    public List<Animale> findByRecinto(int idRecinto) {
        return queryMany("SELECT * FROM animale WHERE id_recinto = ?", statement -> statement.setInt(1, idRecinto));
    }

    public List<Animale> findBySpecie(int idSpecie) {
        return queryMany("SELECT * FROM animale WHERE id_specie = ?", statement -> statement.setInt(1, idSpecie));
    }

    public List<Animale> findAttivi() {
        return queryMany("SELECT * FROM animale WHERE attivo = true", null);
    }

    public List<Animale> findNonAttivi() {
        return queryMany("SELECT * FROM animale WHERE attivo = false", null);
    }

}
