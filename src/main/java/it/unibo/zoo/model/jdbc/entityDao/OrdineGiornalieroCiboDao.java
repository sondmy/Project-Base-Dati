package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.OrdineGiornalieroCibo;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrdineGiornalieroCiboDao extends AbstractCrudDao<OrdineGiornalieroCibo> {

    @Override
    protected String tableName() {
        return "ordine_giornaliero_cibo";
    }

    @Override
    protected String idColumnName() { return "id_ordine"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO ordine_giornaliero_cibo (data_ordine, quantita_kg, id_fornitore, id_tipo_cibo, id_transazione) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE ordine_giornaliero_cibo SET data_ordine = ?, quantita_kg = ?, id_fornitore = ?, id_tipo_cibo = ?, id_transazione = ? WHERE id_ordine = ?";
    }

    @Override
    protected int getId(OrdineGiornalieroCibo entity) {
        return entity.getIdOrdine();
    }

    @Override
    protected void setId(OrdineGiornalieroCibo entity, int id) {
        entity.setIdOrdine(id);
    }

    @Override
    protected OrdineGiornalieroCibo mapRow(ResultSet resultSet) throws SQLException {
        return new OrdineGiornalieroCibo(
                resultSet.getInt("id_ordine"),
                JdbcUtils.getNullableDate(resultSet, "data_ordine"),
                resultSet.getDouble("quantita_kg"),
                resultSet.getInt("id_fornitore"),
                resultSet.getInt("id_tipo_cibo"),
                JdbcUtils.getNullableInteger(resultSet, "id_transazione")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, OrdineGiornalieroCibo entity) throws SQLException {
        int index = 1;
        statement.setDate(index++, java.sql.Date.valueOf(entity.getDataOrdine()));
        statement.setDouble(index++, entity.getQuantitaKg());
        statement.setInt(index++, entity.getIdFornitore());
        statement.setInt(index++, entity.getIdTipoCibo());
        JdbcUtils.setNullableInteger(statement, index++, entity.getIdTransazione());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, OrdineGiornalieroCibo entity) throws SQLException {
        int index = 1;
        statement.setDate(index++, java.sql.Date.valueOf(entity.getDataOrdine()));
        statement.setDouble(index++, entity.getQuantitaKg());
        statement.setInt(index++, entity.getIdFornitore());
        statement.setInt(index++, entity.getIdTipoCibo());
        JdbcUtils.setNullableInteger(statement, index++, entity.getIdTransazione());
        statement.setInt(index++, entity.getIdOrdine());
    }

    public List<OrdineGiornalieroCibo> findByFornitore(int idFornitore) {
        return queryMany("SELECT * FROM ordine_giornaliero_cibo WHERE id_fornitore = ? ORDER BY data_ordine DESC", statement -> statement.setInt(1, idFornitore));
    }

    public List<OrdineGiornalieroCibo> findByTipoCibo(int idTipoCibo) {
        return queryMany("SELECT * FROM ordine_giornaliero_cibo WHERE id_tipo_cibo = ? ORDER BY data_ordine DESC", statement -> statement.setInt(1, idTipoCibo));
    }

    public List<OrdineGiornalieroCibo> findByTransazione(Integer idTransazione) {
        return queryMany("SELECT * FROM ordine_giornaliero_cibo WHERE id_transazione = ? ORDER BY data_ordine DESC", statement -> statement.setInt(1, idTransazione));
    }

    public List<OrdineGiornalieroCibo> findByDataOrdine(LocalDate dataOrdine) {
        return queryMany("SELECT * FROM ordine_giornaliero_cibo WHERE data_ordine = ? ORDER BY id_ordine DESC", statement -> statement.setDate(1, java.sql.Date.valueOf(dataOrdine)));
    }

}
