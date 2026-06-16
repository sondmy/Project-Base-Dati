package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.Area;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AreaDao extends AbstractCrudDao<Area> {

    @Override
    protected String tableName() {
        return "area";
    }

    @Override
    protected String idColumnName() { return "id_area"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO area (nome, metratura, id_tipo_area) VALUES (?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE area SET nome = ?, metratura = ?, id_tipo_area = ? WHERE id_area = ?";
    }

    @Override
    protected int getId(Area entity) {
        return entity.getIdArea();
    }

    @Override
    protected void setId(Area entity, int id) {
        entity.setIdArea(id);
    }

    @Override
    protected Area mapRow(ResultSet resultSet) throws SQLException {
        return new Area(
                resultSet.getInt("id_area"),
                resultSet.getString("nome"),
                resultSet.getInt("metratura"),
                resultSet.getInt("id_tipo_area")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, Area entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        statement.setInt(index++, entity.getMetratura());
        statement.setInt(index++, entity.getIdTipoArea());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, Area entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        statement.setInt(index++, entity.getMetratura());
        statement.setInt(index++, entity.getIdTipoArea());
        statement.setInt(index++, entity.getIdArea());
    }

    public List<Area> findByTipoArea(int idTipoArea) {
        return queryMany("SELECT * FROM area WHERE id_tipo_area = ?", statement -> statement.setInt(1, idTipoArea));
    }

}
