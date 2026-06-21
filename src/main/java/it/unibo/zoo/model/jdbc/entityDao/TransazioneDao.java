package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.Transazione;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TransazioneDao extends AbstractCrudDao<Transazione> {

    @Override
    protected String tableName() {
        return "transazione";
    }

    @Override
    protected String idColumnName() { return "id_transazione"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO transazione (tipo, importo, data, descrizione, id_categoria, id_utente, id_fornitore, id_scontrino) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE transazione SET tipo = ?, importo = ?, data = ?, descrizione = ?, id_categoria = ?, id_utente = ?, id_fornitore = ?, id_scontrino = ? WHERE id_transazione = ?";
    }

    @Override
    protected int getId(Transazione entity) {
        return entity.getIdTransazione();
    }

    @Override
    protected void setId(Transazione entity, int id) {
        entity.setIdTransazione(id);
    }

    @Override
    protected Transazione mapRow(ResultSet resultSet) throws SQLException {
        return new Transazione(
                resultSet.getInt("id_transazione"),
                resultSet.getString("tipo"),
                resultSet.getDouble("importo"),
                JdbcUtils.getNullableDate(resultSet, "data"),
                resultSet.getString("descrizione"),
                resultSet.getInt("id_categoria"),
                resultSet.getInt("id_utente"),
                JdbcUtils.getNullableInteger(resultSet, "id_fornitore"),
                JdbcUtils.getNullableInteger(resultSet, "id_scontrino")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, Transazione entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getTipo());
        statement.setDouble(index++, entity.getImporto());
        statement.setDate(index++, java.sql.Date.valueOf(entity.getData()));
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
        statement.setInt(index++, entity.getIdCategoria());
        statement.setInt(index++, entity.getIdUtente());
        JdbcUtils.setNullableInteger(statement, index++, entity.getIdFornitore());
        JdbcUtils.setNullableInteger(statement, index++, entity.getIdScontrino());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, Transazione entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getTipo());
        statement.setDouble(index++, entity.getImporto());
        statement.setDate(index++, java.sql.Date.valueOf(entity.getData()));
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
        statement.setInt(index++, entity.getIdCategoria());
        statement.setInt(index++, entity.getIdUtente());
        JdbcUtils.setNullableInteger(statement, index++, entity.getIdFornitore());
        JdbcUtils.setNullableInteger(statement, index++, entity.getIdScontrino());
        statement.setInt(index++, entity.getIdTransazione());
    }

    public List<Transazione> findByCategoria(int idCategoria) {
        return queryMany("SELECT * FROM transazione WHERE id_categoria = ? ORDER BY data DESC, id_transazione DESC", statement -> statement.setInt(1, idCategoria));
    }

    public List<Transazione> findByUtente(int idUtente) {
        return queryMany("SELECT * FROM transazione WHERE id_utente = ? ORDER BY data DESC, id_transazione DESC", statement -> statement.setInt(1, idUtente));
    }

    public List<Transazione> findByFornitore(Integer idFornitore) {
        return queryMany("SELECT * FROM transazione WHERE id_fornitore = ? ORDER BY data DESC, id_transazione DESC", statement -> statement.setInt(1, idFornitore));
    }

    public List<Transazione> findByScontrino(Integer idScontrino) {
        return queryMany("SELECT * FROM transazione WHERE id_scontrino = ? ORDER BY data DESC, id_transazione DESC", statement -> statement.setInt(1, idScontrino));
    }

    public List<Transazione> findByTipo(String tipo) {
        return queryMany("SELECT * FROM transazione WHERE tipo = ? ORDER BY data DESC, id_transazione DESC", statement -> statement.setString(1, tipo));
    }

    public List<Transazione> findByData(LocalDate data) {
        return queryMany("SELECT * FROM transazione WHERE data = ? ORDER BY id_transazione DESC", statement -> statement.setDate(1, java.sql.Date.valueOf(data)));
    }

    public List<Transazione> findByDateRange(LocalDate from, LocalDate to) {
        return queryMany("SELECT * FROM transazione WHERE data >= ? AND data <= ? ORDER BY data DESC, id_transazione DESC", statement -> {
            statement.setDate(1, java.sql.Date.valueOf(from));
            statement.setDate(2, java.sql.Date.valueOf(to));
        });
    }

}
