package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.Recinto;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecintoDao extends AbstractCrudDao<Recinto> {

    @Override
    protected String tableName() {
        return "recinto";
    }

    @Override
    protected String idColumnName() { return "id_recinto"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO recinto (capienza_massima, id_area, id_tipo_recinto) VALUES (?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE recinto SET capienza_massima = ?, id_area = ?, id_tipo_recinto = ? WHERE id_recinto = ?";
    }

    @Override
    protected int getId(Recinto entity) {
        return entity.getIdRecinto();
    }

    @Override
    protected void setId(Recinto entity, int id) {
        entity.setIdRecinto(id);
    }

    @Override
    protected Recinto mapRow(ResultSet resultSet) throws SQLException {
        return new Recinto(
                resultSet.getInt("id_recinto"),
                resultSet.getInt("capienza_massima"),
                resultSet.getInt("id_area"),
                resultSet.getInt("id_tipo_recinto")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, Recinto entity) throws SQLException {
        int index = 1;
        statement.setInt(index++, entity.getCapienzaMassima());
        statement.setInt(index++, entity.getIdArea());
        statement.setInt(index++, entity.getIdTipoRecinto());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, Recinto entity) throws SQLException {
        int index = 1;
        statement.setInt(index++, entity.getCapienzaMassima());
        statement.setInt(index++, entity.getIdArea());
        statement.setInt(index++, entity.getIdTipoRecinto());
        statement.setInt(index++, entity.getIdRecinto());
    }

    public List<Recinto> findByArea(int idArea) {
        return queryMany("SELECT * FROM recinto WHERE id_area = ?", statement -> statement.setInt(1, idArea));
    }

    public List<Recinto> findByTipoRecinto(int idTipoRecinto) {
        return queryMany("SELECT * FROM recinto WHERE id_tipo_recinto = ?", statement -> statement.setInt(1, idTipoRecinto));
    }

    public List<Recinto> findByCapienzaMassimaAtLeast(int minCapienza) {
        return queryMany("SELECT * FROM recinto WHERE capienza_massima >= ?", statement -> statement.setInt(1, minCapienza));
    }

}
