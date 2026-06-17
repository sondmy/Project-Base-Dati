package it.unibo.zoo.model.jdbc;

import java.util.List;
import java.util.Optional;

public interface CompositeCrudDao<T> {
    T insert(T entity);
    boolean update(T entity);
    boolean delete(int key1, int key2);
    Optional<T> findById(int key1, int key2);
    List<T> findAll();
}
