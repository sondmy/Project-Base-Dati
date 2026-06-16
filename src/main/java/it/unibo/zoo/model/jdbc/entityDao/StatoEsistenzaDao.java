package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.StatoEsistenza;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StatoEsistenzaDao extends AbstractCrudDao<StatoEsistenza> {

    @Override
    protected String tableName() {
        return "stato_esistenza";
    }

    @Override
    protected String idColumnName() { return "id_stato"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO stato_esistenza (nome, descrizione) VALUES (?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE stato_esistenza SET nome = ?, descrizione = ? WHERE id_stato = ?";
    }

    @Override
    protected int getId(StatoEsistenza entity) {
        return entity.getIdStato();
    }

    @Override
    protected void setId(StatoEsistenza entity, int id) {
        entity.setIdStato(id);
    }

    @Override
    protected StatoEsistenza mapRow(ResultSet resultSet) throws SQLException {
        return new StatoEsistenza(
                resultSet.getInt("id_stato"),
                resultSet.getString("nome"),
                resultSet.getString("descrizione")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, StatoEsistenza entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, StatoEsistenza entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
        statement.setInt(index++, entity.getIdStato());
    }

    public Optional<StatoEsistenza> findByNome(String nome) {
        return queryOne("SELECT * FROM stato_esistenza WHERE nome = ?", statement -> statement.setString(1, nome));
    }

}
