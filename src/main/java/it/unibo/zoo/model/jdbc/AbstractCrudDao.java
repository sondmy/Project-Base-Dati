package it.unibo.zoo.model.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudDao<T> implements CrudDao<T, Integer> {

    protected abstract String tableName();
    protected abstract String idColumnName();
    protected abstract String insertSql();
    protected abstract String updateSql();
    protected abstract void bindInsert(PreparedStatement statement, T entity) throws SQLException;
    protected abstract void bindUpdate(PreparedStatement statement, T entity) throws SQLException;
    protected abstract T mapRow(ResultSet resultSet) throws SQLException;
    protected abstract int getId(T entity);
    protected abstract void setId(T entity, int id);

    protected String selectAllSql() {
        return "SELECT * FROM " + tableName();
    }

    protected String selectByIdSql() {
        return "SELECT * FROM " + tableName() + " WHERE " + idColumnName() + " = ?";
    }

    protected String deleteSql() {
        return "DELETE FROM " + tableName() + " WHERE " + idColumnName() + " = ?";
    }

    @Override
    public T insert(T entity) {
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSql(), Statement.RETURN_GENERATED_KEYS)) {
            bindInsert(statement, entity);
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    setId(entity, keys.getInt(1));
                }
            }
            return entity;
        } catch (SQLException e) {
            throw new DaoException("Errore SQL durante l'insert su " + tableName(), e);
        }
    }

    @Override
    public boolean update(T entity) {
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(updateSql())) {
            bindUpdate(statement, entity);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Errore SQL durante l'update su " + tableName(), e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteSql())) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Errore SQL durante la delete su " + tableName(), e);
        }
    }

    @Override
    public Optional<T> findById(Integer id) {
        List<T> results = queryMany(selectByIdSql(), statement -> statement.setInt(1, id));
        return results.stream().findFirst();
    }

    @Override
    public List<T> findAll() {
        return queryMany(selectAllSql(), null);
    }

    protected List<T> queryMany(String sql, StatementBinder binder) {
        List<T> results = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if (binder != null) {
                binder.bind(statement);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(mapRow(resultSet));
                }
            }
            return results;
        } catch (SQLException e) {
            throw new DaoException("Errore SQL sulla tabella " + tableName(), e);
        }
    }

    protected Optional<T> queryOne(String sql, StatementBinder binder) {
        return queryMany(sql, binder).stream().findFirst();
    }

    protected int executeUpdate(String sql, StatementBinder binder) {
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if (binder != null) {
                binder.bind(statement);
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Errore SQL sulla tabella " + tableName(), e);
        }
    }
}
