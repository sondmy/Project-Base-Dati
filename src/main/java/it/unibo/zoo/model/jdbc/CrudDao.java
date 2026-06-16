package it.unibo.zoo.model.jdbc;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T, ID> {
    T insert(T entity);
    boolean update(T entity);
    boolean delete(ID id);
    Optional<T> findById(ID id);
    List<T> findAll();
}
