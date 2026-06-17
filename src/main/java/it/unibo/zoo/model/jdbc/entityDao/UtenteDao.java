package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.Utente;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UtenteDao extends AbstractCrudDao<Utente> {

    @Override
    protected String tableName() {
        return "utente";
    }

    @Override
    protected String idColumnName() { return "id_utente"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO utente (email, password_hash, data_registrazione, ruolo, id_dipendente) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE utente SET email = ?, password_hash = ?, data_registrazione = ?, ruolo = ?, id_dipendente = ? WHERE id_utente = ?";
    }

    @Override
    protected int getId(Utente entity) {
        return entity.getIdUtente();
    }

    @Override
    protected void setId(Utente entity, int id) {
        entity.setIdUtente(id);
    }

    @Override
    protected Utente mapRow(ResultSet resultSet) throws SQLException {
        return new Utente(
                resultSet.getInt("id_utente"),
                resultSet.getString("email"),
                resultSet.getString("password_hash"),
                JdbcUtils.getNullableDate(resultSet, "data_registrazione"),
                resultSet.getString("ruolo"),
                JdbcUtils.getNullableInteger(resultSet, "id_dipendente")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, Utente entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getEmail());
        statement.setString(index++, entity.getPasswordHash());
        statement.setDate(index++, java.sql.Date.valueOf(entity.getDataRegistrazione()));
        statement.setString(index++, entity.getRuolo());
        JdbcUtils.setNullableInteger(statement, index++, entity.getIdDipendente());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, Utente entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getEmail());
        statement.setString(index++, entity.getPasswordHash());
        statement.setDate(index++, java.sql.Date.valueOf(entity.getDataRegistrazione()));
        statement.setString(index++, entity.getRuolo());
        JdbcUtils.setNullableInteger(statement, index++, entity.getIdDipendente());
        statement.setInt(index++, entity.getIdUtente());
    }

    public List<Utente> findByDipendente(Integer idDipendente) {
        return queryMany("SELECT * FROM utente WHERE id_dipendente = ?", statement -> statement.setInt(1, idDipendente));
    }

    public Optional<Utente> findByEmail(String email) {
        return queryOne("SELECT * FROM utente WHERE email = ?", statement -> statement.setString(1, email));
    }

    public List<Utente> findByRuolo(String ruolo) {
        return queryMany("SELECT * FROM utente WHERE ruolo = ?", statement -> statement.setString(1, ruolo));
    }

}
