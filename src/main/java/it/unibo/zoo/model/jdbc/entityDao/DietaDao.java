package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.Dieta;
import it.unibo.zoo.model.jdbc.AbstractCompositeCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DietaDao extends AbstractCompositeCrudDao<Dieta> {

    @Override
    protected String tableName() { return "dieta"; }

    @Override
    protected String key1ColumnName() { return "id_specie"; }

    @Override
    protected String key2ColumnName() { return "id_tipo_cibo"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO dieta (id_specie, id_tipo_cibo, quantita_kg_giorno) VALUES (?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE dieta SET id_specie = ?, id_tipo_cibo = ?, quantita_kg_giorno = ? WHERE id_specie = ? AND id_tipo_cibo = ?";
    }

    @Override
    protected int getKey1(Dieta entity) { return entity.getIdSpecie(); }

    @Override
    protected int getKey2(Dieta entity) { return entity.getIdTipoCibo(); }

    @Override
    protected void setKey1(Dieta entity, int value) { entity.setIdSpecie(value); }

    @Override
    protected void setKey2(Dieta entity, int value) { entity.setIdTipoCibo(value); }

    @Override
    protected Dieta mapRow(ResultSet resultSet) throws SQLException {
        return new Dieta(
                resultSet.getInt("id_specie"),
                resultSet.getInt("id_tipo_cibo"),
                resultSet.getDouble("quantita_kg_giorno")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, Dieta entity) throws SQLException {
        int index = 1;
        statement.setInt(index++, entity.getIdSpecie());
        statement.setInt(index++, entity.getIdTipoCibo());
        statement.setDouble(index++, entity.getQuantitaKgGiorno());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, Dieta entity) throws SQLException {
        int index = 1;
        statement.setInt(index++, entity.getIdSpecie());
        statement.setInt(index++, entity.getIdTipoCibo());
        statement.setDouble(index++, entity.getQuantitaKgGiorno());
        statement.setInt(index++, entity.getIdSpecie());
        statement.setInt(index++, entity.getIdTipoCibo());
    }

    public List<Dieta> findBySpecie(int idSpecie) {
        return queryMany("SELECT * FROM dieta WHERE id_specie = ?", statement -> statement.setInt(1, idSpecie));
    }

    public List<Dieta> findByTipoCibo(int idTipoCibo) {
        return queryMany("SELECT * FROM dieta WHERE id_tipo_cibo = ?", statement -> statement.setInt(1, idTipoCibo));
    }
}
