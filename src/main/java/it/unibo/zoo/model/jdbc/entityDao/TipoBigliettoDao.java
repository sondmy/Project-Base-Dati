package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.TipoBiglietto;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TipoBigliettoDao extends AbstractCrudDao<TipoBiglietto> {

    @Override
    protected String tableName() {
        return "tipo_biglietto";
    }

    @Override
    protected String idColumnName() { return "id_biglietto"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO tipo_biglietto (nome, descrizione, prezzo, attivo) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE tipo_biglietto SET nome = ?, descrizione = ?, prezzo = ?, attivo = ? WHERE id_biglietto = ?";
    }

    @Override
    protected int getId(TipoBiglietto entity) {
        return entity.getIdBiglietto();
    }

    @Override
    protected void setId(TipoBiglietto entity, int id) {
        entity.setIdBiglietto(id);
    }

    @Override
    protected TipoBiglietto mapRow(ResultSet resultSet) throws SQLException {
        return new TipoBiglietto(
                resultSet.getInt("id_biglietto"),
                resultSet.getString("nome"),
                resultSet.getString("descrizione"),
                resultSet.getDouble("prezzo"),
                resultSet.getBoolean("attivo")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, TipoBiglietto entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
        statement.setDouble(index++, entity.getPrezzo());
        statement.setBoolean(index++, entity.isAttivo());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, TipoBiglietto entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
        statement.setDouble(index++, entity.getPrezzo());
        statement.setBoolean(index++, entity.isAttivo());
        statement.setInt(index++, entity.getIdBiglietto());
    }

    public List<TipoBiglietto> findByAttivo(boolean attivo) {
        return queryMany("SELECT * FROM tipo_biglietto WHERE attivo = ?", statement -> statement.setBoolean(1, attivo));
    }

    public Optional<TipoBiglietto> findByNome(String nome) {
        return queryOne("SELECT * FROM tipo_biglietto WHERE nome = ?", statement -> statement.setString(1, nome));
    }

}
