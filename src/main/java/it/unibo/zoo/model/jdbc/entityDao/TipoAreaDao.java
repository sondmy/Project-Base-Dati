package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.TipoArea;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TipoAreaDao extends AbstractCrudDao<TipoArea> {

    @Override
    protected String tableName() {
        return "tipo_area";
    }

    @Override
    protected String idColumnName() { return "id_tipo_area"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO tipo_area (nome, descrizione) VALUES (?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE tipo_area SET nome = ?, descrizione = ? WHERE id_tipo_area = ?";
    }

    @Override
    protected int getId(TipoArea entity) {
        return entity.getIdTipoArea();
    }

    @Override
    protected void setId(TipoArea entity, int id) {
        entity.setIdTipoArea(id);
    }

    @Override
    protected TipoArea mapRow(ResultSet resultSet) throws SQLException {
        return new TipoArea(
                resultSet.getInt("id_tipo_area"),
                resultSet.getString("nome"),
                resultSet.getString("descrizione")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, TipoArea entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, TipoArea entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
        statement.setInt(index++, entity.getIdTipoArea());
    }

    public Optional<TipoArea> findByNome(String nome) {
        return queryOne("SELECT * FROM tipo_area WHERE nome = ?", statement -> statement.setString(1, nome));
    }

}
