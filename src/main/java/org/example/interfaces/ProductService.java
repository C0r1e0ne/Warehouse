package org.example.interfaces;

import org.example.model.Product;
import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    void addProduct(Product product) throws SQLException;
    Product getProductById(Long id) throws SQLException;
    List<Product> getAllProducts() throws SQLException;
    void updateProduct(Product product) throws SQLException;
    void deleteProduct(Long id) throws SQLException;
}
