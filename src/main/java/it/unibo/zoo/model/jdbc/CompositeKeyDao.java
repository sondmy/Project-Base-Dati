package it.unibo.zoo.model.jdbc;

import java.util.List;
import java.util.Optional;

public interface CompositeKeyDao<T> {
    T insert(T entity);
    boolean update(T entity);
    boolean delete(int firstKey, int secondKey);
    Optional<T> findById(int firstKey, int secondKey);
    List<T> findAll();
}
