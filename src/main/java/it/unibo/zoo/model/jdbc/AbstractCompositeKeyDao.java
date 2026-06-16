package it.unibo.zoo.model.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCompositeKeyDao<T> implements CompositeKeyDao<T> {

    protected abstract String tableName();
    protected abstract String firstKeyColumn();
    protected abstract String secondKeyColumn();
    protected abstract T mapRow(ResultSet rs) throws SQLException;
    protected abstract void bindInsert(PreparedStatement ps, T entity) throws SQLException;
    protected abstract void bindUpdate(PreparedStatement ps, T entity) throws SQLException;
    protected abstract void bindFirstKey(PreparedStatement ps, int index, int value) throws SQLException;
    protected abstract void bindSecondKey(PreparedStatement ps, int index, int value) throws SQLException;
    protected abstract int firstKey(T entity);
    protected abstract int secondKey(T entity);
    protected abstract String updatableColumnsSetClause();

    protected String findByIdSql() {
        return "SELECT * FROM " + tableName() + " WHERE " + firstKeyColumn() + " = ? AND " + secondKeyColumn() + " = ?";
    }

    protected String findAllSql() {
        return "SELECT * FROM " + tableName();
    }

    protected String insertSql() {
        throw new UnsupportedOperationException("insertSql deve essere ridefinito");
    }

    protected String updateSql() {
        return "UPDATE " + tableName() + " SET " + updatableColumnsSetClause() + " WHERE " + firstKeyColumn() + " = ? AND " + secondKeyColumn() + " = ?";
    }

    protected String deleteSql() {
        return "DELETE FROM " + tableName() + " WHERE " + firstKeyColumn() + " = ? AND " + secondKeyColumn() + " = ?";
    }

    protected Connection getConnection() throws SQLException {
        return ConnectionFactory.getInstance().getConnection();
    }

    protected List<T> queryMany(final String sql, final SqlBinder binder) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (binder != null) {
                binder.bind(ps);
            }
            try (ResultSet rs = ps.executeQuery()) {
                final List<T> results = new ArrayList<>();
                while (rs.next()) {
                    results.add(mapRow(rs));
                }
                return results;
            }
        } catch (SQLException e) {
            throw new DaoException("Errore SQL sulla tabella " + tableName(), e);
        }
    }

    protected Optional<T> queryOne(final String sql, final SqlBinder binder) {
        return queryMany(sql, binder).stream().findFirst();
    }

    protected int executeUpdate(final String sql, final SqlBinder binder) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (binder != null) {
                binder.bind(ps);
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Errore SQL sulla tabella " + tableName(), e);
        }
    }

    @Override
    public Optional<T> findById(final int firstKey, final int secondKey) {
        return queryOne(findByIdSql(), ps -> {
            bindFirstKey(ps, 1, firstKey);
            bindSecondKey(ps, 2, secondKey);
        });
    }

    @Override
    public List<T> findAll() {
        return queryMany(findAllSql(), null);
    }

    @Override
    public T insert(final T entity) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(insertSql())) {
            bindInsert(ps, entity);
            ps.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new DaoException("Errore SQL in INSERT su " + tableName(), e);
        }
    }

    @Override
    public boolean update(final T entity) {
        return executeUpdate(updateSql(), ps -> bindUpdate(ps, entity)) > 0;
    }

    @Override
    public boolean delete(final int firstKey, final int secondKey) {
        return executeUpdate(deleteSql(), ps -> {
            bindFirstKey(ps, 1, firstKey);
            bindSecondKey(ps, 2, secondKey);
        }) > 0;
    }

    @FunctionalInterface
    protected interface SqlBinder {
        void bind(PreparedStatement ps) throws SQLException;
    }

    protected static void bindString(final PreparedStatement ps, final int index, final String value) throws SQLException {
        if (value == null) {
            ps.setNull(index, java.sql.Types.VARCHAR);
        } else {
            ps.setString(index, value);
        }
    }

    protected static void bindInteger(final PreparedStatement ps, final int index, final Integer value) throws SQLException {
        if (value == null) {
            ps.setNull(index, java.sql.Types.INTEGER);
        } else {
            ps.setInt(index, value);
        }
    }

    protected static void bindDouble(final PreparedStatement ps, final int index, final Double value) throws SQLException {
        if (value == null) {
            ps.setNull(index, java.sql.Types.DOUBLE);
        } else {
            ps.setDouble(index, value);
        }
    }

    protected static void bindLocalDate(final PreparedStatement ps, final int index, final java.time.LocalDate value) throws SQLException {
        if (value == null) {
            ps.setNull(index, java.sql.Types.DATE);
        } else {
            ps.setObject(index, value);
        }
    }

    protected static void bindLocalDateTime(final PreparedStatement ps, final int index, final java.time.LocalDateTime value) throws SQLException {
        if (value == null) {
            ps.setNull(index, java.sql.Types.TIMESTAMP);
        } else {
            ps.setObject(index, value);
        }
    }

    protected static Integer getInteger(final ResultSet rs, final String column) throws SQLException {
        return rs.getObject(column, Integer.class);
    }

    protected static Double getDoubleObject(final ResultSet rs, final String column) throws SQLException {
        return rs.getObject(column, Double.class);
    }

    protected static java.time.LocalDate getLocalDate(final ResultSet rs, final String column) throws SQLException {
        return rs.getObject(column, java.time.LocalDate.class);
    }

    protected static java.time.LocalDateTime getLocalDateTime(final ResultSet rs, final String column) throws SQLException {
        return rs.getObject(column, java.time.LocalDateTime.class);
    }

    protected static char getChar(final ResultSet rs, final String column) throws SQLException {
        final String value = rs.getString(column);
        return value == null || value.isEmpty() ? '\0' : value.charAt(0);
    }
}
