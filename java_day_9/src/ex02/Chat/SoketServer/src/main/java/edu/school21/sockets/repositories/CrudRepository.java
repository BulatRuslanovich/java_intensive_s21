package edu.school21.sockets.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<K, T> {
    Optional<T> findById(K id);
    List<T> findAll();
    void save(T entity);
    void update(T entity);
    void delete(K id);
}
