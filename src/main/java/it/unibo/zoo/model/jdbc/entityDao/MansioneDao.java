package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.Mansione;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MansioneDao extends AbstractCrudDao<Mansione> {

    @Override
    protected String tableName() {
        return "mansione";
    }

    @Override
    protected String idColumnName() { return "id_mansione"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO mansione (nome, descrizione) VALUES (?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE mansione SET nome = ?, descrizione = ? WHERE id_mansione = ?";
    }

    @Override
    protected int getId(Mansione entity) {
        return entity.getIdMansione();
    }

    @Override
    protected void setId(Mansione entity, int id) {
        entity.setIdMansione(id);
    }

    @Override
    protected Mansione mapRow(ResultSet resultSet) throws SQLException {
        return new Mansione(
                resultSet.getInt("id_mansione"),
                resultSet.getString("nome"),
                resultSet.getString("descrizione")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, Mansione entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, Mansione entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
        statement.setInt(index++, entity.getIdMansione());
    }

    public Optional<Mansione> findByNome(String nome) {
        return queryOne("SELECT * FROM mansione WHERE nome = ?", statement -> statement.setString(1, nome));
    }

}
