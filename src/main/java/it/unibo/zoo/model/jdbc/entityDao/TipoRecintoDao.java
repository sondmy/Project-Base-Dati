package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.TipoRecinto;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TipoRecintoDao extends AbstractCrudDao<TipoRecinto> {

    @Override
    protected String tableName() {
        return "tipo_recinto";
    }

    @Override
    protected String idColumnName() { return "id_tipo_recinto"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO tipo_recinto (nome, descrizione) VALUES (?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE tipo_recinto SET nome = ?, descrizione = ? WHERE id_tipo_recinto = ?";
    }

    @Override
    protected int getId(TipoRecinto entity) {
        return entity.getIdTipoRecinto();
    }

    @Override
    protected void setId(TipoRecinto entity, int id) {
        entity.setIdTipoRecinto(id);
    }

    @Override
    protected TipoRecinto mapRow(ResultSet resultSet) throws SQLException {
        return new TipoRecinto(
                resultSet.getInt("id_tipo_recinto"),
                resultSet.getString("nome"),
                resultSet.getString("descrizione")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, TipoRecinto entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, TipoRecinto entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
        statement.setInt(index++, entity.getIdTipoRecinto());
    }

    public Optional<TipoRecinto> findByNome(String nome) {
        return queryOne("SELECT * FROM tipo_recinto WHERE nome = ?", statement -> statement.setString(1, nome));
    }

}
