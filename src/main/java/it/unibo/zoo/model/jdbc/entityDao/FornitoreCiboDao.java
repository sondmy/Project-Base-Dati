package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.FornitoreCibo;
import it.unibo.zoo.model.jdbc.AbstractCompositeCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FornitoreCiboDao extends AbstractCompositeCrudDao<FornitoreCibo> {

    @Override
    protected String tableName() { return "fornitore_cibo"; }

    @Override
    protected String key1ColumnName() { return "id_fornitore"; }

    @Override
    protected String key2ColumnName() { return "id_tipo_cibo"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO fornitore_cibo (id_fornitore, id_tipo_cibo) VALUES (?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE fornitore_cibo SET id_fornitore = ?, id_tipo_cibo = ? WHERE id_fornitore = ? AND id_tipo_cibo = ?";
    }

    @Override
    protected int getKey1(FornitoreCibo entity) { return entity.getIdFornitore(); }

    @Override
    protected int getKey2(FornitoreCibo entity) { return entity.getIdTipoCibo(); }

    @Override
    protected void setKey1(FornitoreCibo entity, int value) { entity.setIdFornitore(value); }

    @Override
    protected void setKey2(FornitoreCibo entity, int value) { entity.setIdTipoCibo(value); }

    @Override
    protected FornitoreCibo mapRow(ResultSet resultSet) throws SQLException {
        return new FornitoreCibo(
                resultSet.getInt("id_fornitore"),
                resultSet.getInt("id_tipo_cibo")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, FornitoreCibo entity) throws SQLException {
        int index = 1;
        statement.setInt(index++, entity.getIdFornitore());
        statement.setInt(index++, entity.getIdTipoCibo());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, FornitoreCibo entity) throws SQLException {
        int index = 1;
        statement.setInt(index++, entity.getIdFornitore());
        statement.setInt(index++, entity.getIdTipoCibo());
        statement.setInt(index++, entity.getIdFornitore());
        statement.setInt(index++, entity.getIdTipoCibo());
    }

    public List<FornitoreCibo> findByFornitore(int idFornitore) {
        return queryMany("SELECT * FROM fornitore_cibo WHERE id_fornitore = ?", statement -> statement.setInt(1, idFornitore));
    }

    public List<FornitoreCibo> findByTipoCibo(int idTipoCibo) {
        return queryMany("SELECT * FROM fornitore_cibo WHERE id_tipo_cibo = ?", statement -> statement.setInt(1, idTipoCibo));
    }
}
