package org.example.repository;

import org.example.interfaces.ProductMovementRepository;
import org.example.model.ProductMovement;
import org.example.connection.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductMovementRepositoryImpl implements ProductMovementRepository {

    @Override
    public void create(ProductMovement movement) throws SQLException {
        String sql = "INSERT INTO productmovement (productid, warehouseid, operationid, quantity, date) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, movement.getProductID());
            stmt.setLong(2, movement.getWarehouseID());
            stmt.setLong(3, movement.getOperationID());
            stmt.setDouble(4, movement.getQuantity());
            stmt.setDate(5, Date.valueOf(movement.getDate()));

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

    @Override
    public ProductMovement findById(Long id) throws SQLException {
        String sql = "SELECT * FROM productmovement WHERE movementid = ?";
        ProductMovement movement = null;

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                movement = new ProductMovement();
                movement.setMovementID(rs.getLong("movementid"));
                movement.setProductID(rs.getLong("productid"));
                movement.setWarehouseID(rs.getLong("warehouseid"));
                movement.setOperationID(rs.getLong("operationid"));
                movement.setQuantity(rs.getDouble("quantity"));
                movement.setDate(rs.getDate("date").toLocalDate());
            }
        }
        return movement;
    }

    @Override
    public List<ProductMovement> findAll() throws SQLException {
        String sql = "SELECT * FROM productmovement";
        List<ProductMovement> movements = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProductMovement movement = new ProductMovement();
                movement.setMovementID(rs.getLong("movementid"));
                movement.setProductID(rs.getLong("productid"));
                movement.setWarehouseID(rs.getLong("warehouseid"));
                movement.setOperationID(rs.getLong("operationid"));
                movement.setQuantity(rs.getDouble("quantity"));
                movement.setDate(rs.getDate("date").toLocalDate());
                movements.add(movement);
            }
        }
        return movements;
    }

    @Override
    public void update(ProductMovement movement) throws SQLException {
        String sql = "UPDATE productmovement SET productid = ?, warehouseid = ?, operationid = ?, quantity = ?, date = ? WHERE movementid = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, movement.getProductID());
            stmt.setLong(2, movement.getWarehouseID());
            stmt.setLong(3, movement.getOperationID());
            stmt.setDouble(4, movement.getQuantity());
            stmt.setDate(5, Date.valueOf(movement.getDate()));
            stmt.setLong(6, movement.getMovementID());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM productmovement WHERE movementid = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
