package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.FamigliaSpecie;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class FamigliaSpecieDao extends AbstractCrudDao<FamigliaSpecie> {

    @Override
    protected String tableName() {
        return "famiglia_specie";
    }

    @Override
    protected String idColumnName() { return "id_famiglia_specie"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO famiglia_specie (nome, descrizione) VALUES (?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE famiglia_specie SET nome = ?, descrizione = ? WHERE id_famiglia_specie = ?";
    }

    @Override
    protected int getId(FamigliaSpecie entity) {
        return entity.getIdFamigliaSpecie();
    }

    @Override
    protected void setId(FamigliaSpecie entity, int id) {
        entity.setIdFamigliaSpecie(id);
    }

    @Override
    protected FamigliaSpecie mapRow(ResultSet resultSet) throws SQLException {
        return new FamigliaSpecie(
                resultSet.getInt("id_famiglia_specie"),
                resultSet.getString("nome"),
                resultSet.getString("descrizione")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, FamigliaSpecie entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, FamigliaSpecie entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
        statement.setInt(index++, entity.getIdFamigliaSpecie());
    }

    public Optional<FamigliaSpecie> findByNome(String nome) {
        return queryOne("SELECT * FROM famiglia_specie WHERE nome = ?", statement -> statement.setString(1, nome));
    }

}
