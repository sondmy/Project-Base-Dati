package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.Dipendente;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DipendenteDao extends AbstractCrudDao<Dipendente> {

    @Override
    protected String tableName() {
        return "dipendente";
    }

    @Override
    protected String idColumnName() { return "id_dipendente"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO dipendente (codice_fiscale, nome, cognome, data_nascita, data_assunzione, prezzo_orario, id_mansione) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE dipendente SET codice_fiscale = ?, nome = ?, cognome = ?, data_nascita = ?, data_assunzione = ?, prezzo_orario = ?, id_mansione = ? WHERE id_dipendente = ?";
    }

    @Override
    protected int getId(Dipendente entity) {
        return entity.getIdDipendente();
    }

    @Override
    protected void setId(Dipendente entity, int id) {
        entity.setIdDipendente(id);
    }

    @Override
    protected Dipendente mapRow(ResultSet resultSet) throws SQLException {
        return new Dipendente(
                resultSet.getInt("id_dipendente"),
                resultSet.getString("codice_fiscale"),
                resultSet.getString("nome"),
                resultSet.getString("cognome"),
                JdbcUtils.getNullableDate(resultSet, "data_nascita"),
                JdbcUtils.getNullableDate(resultSet, "data_assunzione"),
                resultSet.getDouble("prezzo_orario"),
                resultSet.getInt("id_mansione")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, Dipendente entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getCodiceFiscale());
        statement.setString(index++, entity.getNome());
        statement.setString(index++, entity.getCognome());
        JdbcUtils.setNullableDate(statement, index++, entity.getDataNascita());
        statement.setDate(index++, java.sql.Date.valueOf(entity.getDataAssunzione()));
        statement.setDouble(index++, entity.getPrezzoOrario());
        statement.setInt(index++, entity.getIdMansione());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, Dipendente entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getCodiceFiscale());
        statement.setString(index++, entity.getNome());
        statement.setString(index++, entity.getCognome());
        JdbcUtils.setNullableDate(statement, index++, entity.getDataNascita());
        statement.setDate(index++, java.sql.Date.valueOf(entity.getDataAssunzione()));
        statement.setDouble(index++, entity.getPrezzoOrario());
        statement.setInt(index++, entity.getIdMansione());
        statement.setInt(index++, entity.getIdDipendente());
    }

    public List<Dipendente> findByMansione(int idMansione) {
        return queryMany("SELECT * FROM dipendente WHERE id_mansione = ?", statement -> statement.setInt(1, idMansione));
    }

    public Optional<Dipendente> findByCodiceFiscale(String codiceFiscale) {
        return queryOne("SELECT * FROM dipendente WHERE codice_fiscale = ?", statement -> statement.setString(1, codiceFiscale));
    }

}
