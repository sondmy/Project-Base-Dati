package it.unibo.zoo.model.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCompositeCrudDao<T> implements CompositeCrudDao<T> {

    protected abstract String tableName();
    protected abstract String key1ColumnName();
    protected abstract String key2ColumnName();
    protected abstract String insertSql();
    protected abstract String updateSql();
    protected abstract void bindInsert(PreparedStatement statement, T entity) throws SQLException;
    protected abstract void bindUpdate(PreparedStatement statement, T entity) throws SQLException;
    protected abstract T mapRow(ResultSet resultSet) throws SQLException;
    protected abstract int getKey1(T entity);
    protected abstract int getKey2(T entity);
    protected abstract void setKey1(T entity, int value);
    protected abstract void setKey2(T entity, int value);

    protected String selectAllSql() {
        return "SELECT * FROM " + tableName();
    }

    protected String selectByIdSql() {
        return "SELECT * FROM " + tableName() + " WHERE " + key1ColumnName() + " = ? AND " + key2ColumnName() + " = ?";
    }

    protected String deleteSql() {
        return "DELETE FROM " + tableName() + " WHERE " + key1ColumnName() + " = ? AND " + key2ColumnName() + " = ?";
    }

    @Override
    public T insert(T entity) {
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSql(), Statement.RETURN_GENERATED_KEYS)) {
            bindInsert(statement, entity);
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    // per tabelle con chiave composta non ci sono chiavi generate
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
    public boolean delete(int key1, int key2) {
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteSql())) {
            statement.setInt(1, key1);
            statement.setInt(2, key2);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Errore SQL durante la delete su " + tableName(), e);
        }
    }

    @Override
    public Optional<T> findById(int key1, int key2) {
        return queryMany(selectByIdSql(), statement -> {
            statement.setInt(1, key1);
            statement.setInt(2, key2);
        }).stream().findFirst();
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
}
