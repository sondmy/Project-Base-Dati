package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.TipoCibo;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TipoCiboDao extends AbstractCrudDao<TipoCibo> {

    @Override
    protected String tableName() {
        return "tipo_cibo";
    }

    @Override
    protected String idColumnName() { return "id_tipo_cibo"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO tipo_cibo (nome, descrizione) VALUES (?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE tipo_cibo SET nome = ?, descrizione = ? WHERE id_tipo_cibo = ?";
    }

    @Override
    protected int getId(TipoCibo entity) {
        return entity.getIdTipoCibo();
    }

    @Override
    protected void setId(TipoCibo entity, int id) {
        entity.setIdTipoCibo(id);
    }

    @Override
    protected TipoCibo mapRow(ResultSet resultSet) throws SQLException {
        return new TipoCibo(
                resultSet.getInt("id_tipo_cibo"),
                resultSet.getString("nome"),
                resultSet.getString("descrizione")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, TipoCibo entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, TipoCibo entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
        statement.setInt(index++, entity.getIdTipoCibo());
    }

    public Optional<TipoCibo> findByNome(String nome) {
        return queryOne("SELECT * FROM tipo_cibo WHERE nome = ?", statement -> statement.setString(1, nome));
    }

}
