package org.example.dao;

import org.example.model.ProductMovement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductMovementDAO {

    public void createProductMovement(ProductMovement movement) throws SQLException {
        String sql = "INSERT INTO productmovement (productID, warehouseID, operationID, quantity, date) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, movement.getProductID());
            statement.setLong(2, movement.getWarehouseID());
            statement.setLong(3, movement.getOperationID());
            statement.setDouble(4, movement.getQuantity());
            statement.setDate(5, new java.sql.Date(movement.getDate().getTime()));
            statement.executeUpdate();
        }
    }

    public ProductMovement getProductMovementById(Long id) throws SQLException {
        String sql = "SELECT * FROM productmovement WHERE movementID = ?";
        ProductMovement movement = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                movement = new ProductMovement();
                movement.setMovementID(resultSet.getLong("movementID"));
                movement.setProductID(resultSet.getLong("productID"));
                movement.setWarehouseID(resultSet.getLong("warehouseID"));
                movement.setOperationID(resultSet.getLong("operationID"));
                movement.setQuantity(resultSet.getInt("quantity"));
                movement.setDate(resultSet.getDate("date"));
            }
        }

        return movement;
    }

    public List<ProductMovement> getAllProductMovements() throws SQLException {
        String sql = "SELECT * FROM productmovement";
        List<ProductMovement> movements = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ProductMovement movement = new ProductMovement();
                movement.setMovementID(resultSet.getLong("movementID"));
                movement.setProductID(resultSet.getLong("productID"));
                movement.setWarehouseID(resultSet.getLong("warehouseID"));
                movement.setOperationID(resultSet.getLong("operationID"));
                movement.setQuantity(resultSet.getInt("quantity"));
                movement.setDate(resultSet.getDate("date"));
                movements.add(movement);
            }
        }

        return movements;
    }

    public void updateProductMovement(ProductMovement movement) throws SQLException {
        String sql = "UPDATE productmovement SET productID = ?, warehouseID = ?, operationID = ?, quantity = ?, date = ? WHERE movementID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, movement.getProductID());
            statement.setLong(2, movement.getWarehouseID());
            statement.setLong(3, movement.getOperationID());
            statement.setDouble(4, movement.getQuantity());
            statement.setDate(5, new java.sql.Date(movement.getDate().getTime()));
            statement.setLong(6, movement.getMovementID());
            statement.executeUpdate();
        }
    }

    public void deleteProductMovement(Long id) throws SQLException {
        String sql = "DELETE FROM productmovement WHERE movementID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}
