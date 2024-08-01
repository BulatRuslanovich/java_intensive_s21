package edu.school21.dao;

import edu.school21.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    void update(Product product);
    void save(Product product);
    void delete(Long id);
}
