package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.Specie;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SpecieDao extends AbstractCrudDao<Specie> {

    @Override
    protected String tableName() {
        return "specie";
    }

    @Override
    protected String idColumnName() { return "id_specie"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO specie (nome_scientifico, nome_comune, id_habitat, id_stato, id_famiglia_specie) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE specie SET nome_scientifico = ?, nome_comune = ?, id_habitat = ?, id_stato = ?, id_famiglia_specie = ? WHERE id_specie = ?";
    }

    @Override
    protected int getId(Specie entity) {
        return entity.getIdSpecie();
    }

    @Override
    protected void setId(Specie entity, int id) {
        entity.setIdSpecie(id);
    }

    @Override
    protected Specie mapRow(ResultSet resultSet) throws SQLException {
        return new Specie(
                resultSet.getInt("id_specie"),
                resultSet.getString("nome_scientifico"),
                resultSet.getString("nome_comune"),
                JdbcUtils.getNullableInteger(resultSet, "id_habitat"),
                JdbcUtils.getNullableInteger(resultSet, "id_stato"),
                JdbcUtils.getNullableInteger(resultSet, "id_famiglia_specie")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, Specie entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNomeScentifico());
        statement.setString(index++, entity.getNomeComune());
        JdbcUtils.setNullableInteger(statement, index++, entity.getIdHabitat());
        JdbcUtils.setNullableInteger(statement, index++, entity.getIdStato());
        JdbcUtils.setNullableInteger(statement, index++, entity.getIdFamigliaSpecie());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, Specie entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNomeScentifico());
        statement.setString(index++, entity.getNomeComune());
        JdbcUtils.setNullableInteger(statement, index++, entity.getIdHabitat());
        JdbcUtils.setNullableInteger(statement, index++, entity.getIdStato());
        JdbcUtils.setNullableInteger(statement, index++, entity.getIdFamigliaSpecie());
        statement.setInt(index++, entity.getIdSpecie());
    }

    public List<Specie> findByHabitat(Integer idHabitat) {
        return queryMany("SELECT * FROM specie WHERE id_habitat = ?", statement -> statement.setInt(1, idHabitat));
    }

    public List<Specie> findByStato(Integer idStato) {
        return queryMany("SELECT * FROM specie WHERE id_stato = ?", statement -> statement.setInt(1, idStato));
    }

    public List<Specie> findByFamigliaSpecie(Integer idFamigliaSpecie) {
        return queryMany("SELECT * FROM specie WHERE id_famiglia_specie = ?", statement -> statement.setInt(1, idFamigliaSpecie));
    }

}
