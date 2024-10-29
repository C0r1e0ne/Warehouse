package org.example.dao;

import org.example.model.ProductMovement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductMovementDAO {

    public void createProductMovement(ProductMovement movement) throws SQLException {
        String query = "INSERT INTO ProductMovement (productID, warehouseID, operationID, quantity, date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, movement.getProductID());
            stmt.setLong(2, movement.getWarehouseID());
            stmt.setLong(3, movement.getOperationID());
            stmt.setDouble(4, movement.getQuantity());
            stmt.setDate(5, new java.sql.Date(movement.getDate().getTime()));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        movement.setMovementID(generatedKeys.getLong(1));
                    }
                }
            }
        }
    }
    public ProductMovement getProductMovementById(Long movementId) throws SQLException {
        String query = "SELECT * FROM ProductMovement WHERE movementID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, movementId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ProductMovement movement = new ProductMovement();
                    movement.setMovementID(rs.getLong("movementID"));
                    movement.setProductID(rs.getLong("productID"));
                    movement.setWarehouseID(rs.getLong("warehouseID"));
                    movement.setOperationID(rs.getLong("operationID"));
                    movement.setQuantity(rs.getLong("quantity"));
                    movement.setDate(rs.getDate("date"));
                    return movement;
                }
            }
        }
        return null;
    }

    public void updateProductMovement(ProductMovement movement) throws SQLException {
        String query = "UPDATE ProductMovement SET productID = ?, warehouseID = ?, operationID = ?, quantity = ?, date = ? WHERE movementID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, movement.getProductID());
            stmt.setLong(2, movement.getWarehouseID());
            stmt.setLong(3, movement.getOperationID());
            stmt.setDouble(4, movement.getQuantity());
            stmt.setDate(5, new java.sql.Date(movement.getDate().getTime()));
            stmt.setLong(6, movement.getMovementID());

            stmt.executeUpdate();
        }
    }

    public void deleteProductMovement(Long movementId) throws SQLException {
        String query = "DELETE FROM ProductMovement WHERE movementID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, movementId);
            stmt.executeUpdate();
        }
    }

    public List<ProductMovement> getAllProductMovements() throws SQLException {
        String query = "SELECT * FROM ProductMovement";
        List<ProductMovement> movements = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                ProductMovement movement = new ProductMovement();
                movement.setMovementID(rs.getLong("movementID"));
                movement.setProductID(rs.getLong("productID"));
                movement.setWarehouseID(rs.getLong("warehouseID"));
                movement.setOperationID(rs.getLong("operationID"));
                movement.setQuantity(rs.getDouble("quantity"));
                movement.setDate(rs.getDate("date"));
                movements.add(movement);
            }
        }
        return movements;
    }
}
