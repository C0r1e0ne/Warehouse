package org.example.interfaces;

import org.example.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductRepository {
    void create(Product product) throws SQLException;
    Product findById(Long id) throws SQLException;
    List<Product> findAll() throws SQLException;
    void update(Product product) throws SQLException;
    void delete(Long id) throws SQLException;
}
