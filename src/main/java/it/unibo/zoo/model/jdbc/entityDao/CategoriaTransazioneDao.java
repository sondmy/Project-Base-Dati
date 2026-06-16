package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.CategoriaTransazione;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CategoriaTransazioneDao extends AbstractCrudDao<CategoriaTransazione> {

    @Override
    protected String tableName() {
        return "categoria_transazione";
    }

    @Override
    protected String idColumnName() { return "id_categoria"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO categoria_transazione (nome, descrizione, tipo) VALUES (?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE categoria_transazione SET nome = ?, descrizione = ?, tipo = ? WHERE id_categoria = ?";
    }

    @Override
    protected int getId(CategoriaTransazione entity) {
        return entity.getIdCategoria();
    }

    @Override
    protected void setId(CategoriaTransazione entity, int id) {
        entity.setIdCategoria(id);
    }

    @Override
    protected CategoriaTransazione mapRow(ResultSet resultSet) throws SQLException {
        return new CategoriaTransazione(
                resultSet.getInt("id_categoria"),
                resultSet.getString("nome"),
                resultSet.getString("descrizione"),
                resultSet.getString("tipo")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, CategoriaTransazione entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
        statement.setString(index++, entity.getTipo());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, CategoriaTransazione entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
        statement.setString(index++, entity.getTipo());
        statement.setInt(index++, entity.getIdCategoria());
    }

    public List<CategoriaTransazione> findByTipo(String tipo) {
        return queryMany("SELECT * FROM categoria_transazione WHERE tipo = ?", statement -> statement.setString(1, tipo));
    }

    public Optional<CategoriaTransazione> findByNome(String nome) {
        return queryOne("SELECT * FROM categoria_transazione WHERE nome = ?", statement -> statement.setString(1, nome));
    }

}
