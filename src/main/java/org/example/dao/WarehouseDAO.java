package org.example.dao;

import org.example.model.Warehouse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarehouseDAO {

    public void createWarehouse(Warehouse warehouse) throws SQLException {
        String query = "INSERT INTO Warehouse (name, location) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, warehouse.getName());
            stmt.setString(2, warehouse.getLocation());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        warehouse.setWarehouseID(generatedKeys.getLong(1));
                    }
                }
            }
        }
    }

    public Warehouse getWarehouseById(Long warehouseId) throws SQLException {
        String query = "SELECT * FROM Warehouse WHERE warehouseID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, warehouseId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Warehouse warehouse = new Warehouse();
                    warehouse.setWarehouseID(rs.getLong("warehouseID"));
                    warehouse.setName(rs.getString("name"));
                    warehouse.setLocation(rs.getString("location"));
                    return warehouse;
                }
            }
        }
        return null;
    }

    public void updateWarehouse(Warehouse warehouse) throws SQLException {
        String query = "UPDATE Warehouse SET name = ?, location = ? WHERE warehouseID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, warehouse.getName());
            stmt.setString(2, warehouse.getLocation());
            stmt.setLong(3, warehouse.getWarehouseID());
            stmt.executeUpdate();
        }
    }

    public void deleteWarehouse(Long warehouseId) throws SQLException {
        String query = "DELETE FROM Warehouse WHERE warehouseID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, warehouseId);
            stmt.executeUpdate();
        }
    }

    public List<Warehouse> getAllWarehouses() throws SQLException {
        String query = "SELECT * FROM Warehouse";
        List<Warehouse> warehouses = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Warehouse warehouse = new Warehouse();
                warehouse.setWarehouseID(rs.getLong("warehouseID"));
                warehouse.setName(rs.getString("name"));
                warehouse.setLocation(rs.getString("location"));
                warehouses.add(warehouse);
            }
        }
        return warehouses;
    }
}

