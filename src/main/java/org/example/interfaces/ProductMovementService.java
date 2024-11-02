package org.example.interfaces;

import org.example.model.ProductMovement;
import java.sql.SQLException;
import java.util.List;

public interface ProductMovementService {
    void addProductMovement(ProductMovement movement) throws SQLException;
    ProductMovement getProductMovementById(Long id) throws SQLException;
    List<ProductMovement> getAllProductMovements() throws SQLException;
    void updateProductMovement(ProductMovement movement) throws SQLException;
    void deleteProductMovement(Long id) throws SQLException;
}
