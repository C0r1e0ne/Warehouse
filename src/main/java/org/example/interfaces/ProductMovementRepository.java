package org.example.interfaces;

import org.example.model.ProductMovement;

import java.sql.SQLException;
import java.util.List;

public interface ProductMovementRepository {
    void create(ProductMovement movement) throws SQLException;
    ProductMovement findById(Long id) throws SQLException;
    List<ProductMovement> findAll() throws SQLException;
    void update(ProductMovement movement) throws SQLException;
    void delete(Long id) throws SQLException;
    List<ProductMovement> findByWarehouseId(Long warehouseId) throws SQLException;
}
