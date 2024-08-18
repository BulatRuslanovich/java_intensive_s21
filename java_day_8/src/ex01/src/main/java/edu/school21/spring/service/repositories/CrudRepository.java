package edu.school21.spring.service.repositories;

import edu.school21.spring.service.models.User;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<K, T> {
    Optional<T> findById(K id);
    List<T> findAll();
    void save(T entity);
    void update(T entity);
    void delete(K id);
}
