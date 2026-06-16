package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.TipoFornitura;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TipoFornituraDao extends AbstractCrudDao<TipoFornitura> {

    @Override
    protected String tableName() {
        return "tipo_fornitura";
    }

    @Override
    protected String idColumnName() { return "id_tipo_fornitura"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO tipo_fornitura (nome, descrizione) VALUES (?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE tipo_fornitura SET nome = ?, descrizione = ? WHERE id_tipo_fornitura = ?";
    }

    @Override
    protected int getId(TipoFornitura entity) {
        return entity.getIdTipoFornitura();
    }

    @Override
    protected void setId(TipoFornitura entity, int id) {
        entity.setIdTipoFornitura(id);
    }

    @Override
    protected TipoFornitura mapRow(ResultSet resultSet) throws SQLException {
        return new TipoFornitura(
                resultSet.getInt("id_tipo_fornitura"),
                resultSet.getString("nome"),
                resultSet.getString("descrizione")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, TipoFornitura entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, TipoFornitura entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
        statement.setInt(index++, entity.getIdTipoFornitura());
    }

    public Optional<TipoFornitura> findByNome(String nome) {
        return queryOne("SELECT * FROM tipo_fornitura WHERE nome = ?", statement -> statement.setString(1, nome));
    }

}
