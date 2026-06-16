package it.unibo.zoo.model.jdbc.entityDao;

import it.unibo.zoo.model.entity.Habitat;
import it.unibo.zoo.model.jdbc.AbstractCrudDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class HabitatDao extends AbstractCrudDao<Habitat> {

    @Override
    protected String tableName() {
        return "habitat";
    }

    @Override
    protected String idColumnName() { return "id_habitat"; }

    @Override
    protected String insertSql() {
        return "INSERT INTO habitat (nome, descrizione) VALUES (?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE habitat SET nome = ?, descrizione = ? WHERE id_habitat = ?";
    }

    @Override
    protected int getId(Habitat entity) {
        return entity.getIdHabitat();
    }

    @Override
    protected void setId(Habitat entity, int id) {
        entity.setIdHabitat(id);
    }

    @Override
    protected Habitat mapRow(ResultSet resultSet) throws SQLException {
        return new Habitat(
                resultSet.getInt("id_habitat"),
                resultSet.getString("nome"),
                resultSet.getString("descrizione")
        );
    }

    @Override
    protected void bindInsert(PreparedStatement statement, Habitat entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
    }

    @Override
    protected void bindUpdate(PreparedStatement statement, Habitat entity) throws SQLException {
        int index = 1;
        statement.setString(index++, entity.getNome());
        JdbcUtils.setNullableString(statement, index++, entity.getDescrizione());
        statement.setInt(index++, entity.getIdHabitat());
    }

    public Optional<Habitat> findByNome(String nome) {
        return queryOne("SELECT * FROM habitat WHERE nome = ?", statement -> statement.setString(1, nome));
    }

}
