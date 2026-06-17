package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.Fornitore;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FornitoreDao extends AbstractCrudDao<Fornitore> {

    @Override
    protected String tableName() {
        return "fornitore";
    }

    @Override
    protected String idColumnName() { return "id_fornitore"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO fornitore (nome_azienda, descrizione, indirizzo, iban, id_tipo_fornitura) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE fornitore SET nome_azienda = ?, descrizione = ?, indirizzo = ?, iban = ?, id_tipo_fornitura = ? WHERE id_fornitore = ?";
    }

    @Override
    protected int getId(Fornitore entity) {
        return entity.getIdFornitore();
    }

    @Override
    protected void setId(Fornitore entity, int id) {
        entity.setIdFornitore(id);
    }

    @Override
    protected Fornitore mapRow(ResultSet resultSet) throws SQLException {
        return new Fornitore(
                resultSet.getInt("id_fornitore"),
                resultSet.getString("nome_azienda"),
                resultSet.getString("descrizione"),
                resultSet.getString("indirizzo"),
                resultSet.getString("iban"),
                JdbcUtils.getNullableInteger(resultSet, "id_tipo_fornitura")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, Fornitore entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNomeAzienda());
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
        JdbcUtils.setNullableString(statement, index++, entity.getIndirizzo());
        JdbcUtils.setNullableString(statement, index++, entity.getIban());
        JdbcUtils.setNullableInteger(statement, index++, entity.getIdTipoFornitura());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, Fornitore entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNomeAzienda());
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
        JdbcUtils.setNullableString(statement, index++, entity.getIndirizzo());
        JdbcUtils.setNullableString(statement, index++, entity.getIban());
        JdbcUtils.setNullableInteger(statement, index++, entity.getIdTipoFornitura());
        statement.setInt(index++, entity.getIdFornitore());
    }

    public List<Fornitore> findByTipoFornitura(Integer idTipoFornitura) {
        return queryMany("SELECT * FROM fornitore WHERE id_tipo_fornitura = ?", statement -> statement.setInt(1, idTipoFornitura));
    }

}
