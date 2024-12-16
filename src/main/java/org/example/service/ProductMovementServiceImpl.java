package org.example.service;

import org.example.di.ApplicationContext;
import org.example.model.ProductMovement;
import org.example.interfaces.ProductMovementRepository;
import org.example.interfaces.ProductMovementService;

import java.sql.SQLException;
import java.util.List;

public class ProductMovementServiceImpl implements ProductMovementService {

    private final ProductMovementRepository productMovementRepository;

    public ProductMovementServiceImpl(ApplicationContext context) {
        this.productMovementRepository = context.getProductMovementRepository();
    }

    @Override
    public void addProductMovement(ProductMovement movement) throws SQLException {
        if (movement.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        productMovementRepository.create(movement);
    }

    @Override
    public ProductMovement getProductMovementById(Long id) throws SQLException {
        ProductMovement movement = productMovementRepository.findById(id);
        if (movement == null) {
            throw new SQLException("Product movement not found with ID: " + id);
        }
        return movement;
    }

    @Override
    public List<ProductMovement> getAllProductMovements() throws SQLException {
        return productMovementRepository.findAll();
    }

    @Override
    public void updateProductMovement(ProductMovement movement) throws SQLException {
        if (movement.getMovementID() == null) {
            throw new IllegalArgumentException("Movement ID cannot be null.");
        }
        productMovementRepository.update(movement);
    }

    @Override
    public void deleteProductMovement(Long id) throws SQLException {
        ProductMovement movement = getProductMovementById(id);
        if (movement == null) {
            throw new SQLException("Cannot delete: Product movement not found with ID: " + id);
        }
        productMovementRepository.delete(id);
    }
}