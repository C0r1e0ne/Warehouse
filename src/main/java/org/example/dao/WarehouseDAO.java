package org.example.dao;

import org.example.model.Warehouse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarehouseDAO {

    public void createWarehouse(Warehouse warehouse) throws SQLException {
        String sql = "INSERT INTO warehouse (name, location) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, warehouse.getName());
            statement.setString(2, warehouse.getLocation());
            statement.executeUpdate();
        }
    }

    public Warehouse getWarehouseById(Long id) throws SQLException {
        String sql = "SELECT * FROM warehouse WHERE warehouseID = ?";
        Warehouse warehouse = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                warehouse = new Warehouse();
                warehouse.setWarehouseID(resultSet.getLong("warehouseID"));
                warehouse.setName(resultSet.getString("name"));
                warehouse.setLocation(resultSet.getString("location"));
            }
        }

        return warehouse;
    }

    public List<Warehouse> getAllWarehouses() throws SQLException {
        String sql = "SELECT * FROM warehouse";
        List<Warehouse> warehouses = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Warehouse warehouse = new Warehouse();
                warehouse.setWarehouseID(resultSet.getLong("warehouseID"));
                warehouse.setName(resultSet.getString("name"));
                warehouse.setLocation(resultSet.getString("location"));
                warehouses.add(warehouse);
            }
        }

        return warehouses;
    }

    public void updateWarehouse(Warehouse warehouse) throws SQLException {
        String sql = "UPDATE warehouse SET name = ?, location = ? WHERE warehouseID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, warehouse.getName());
            statement.setString(2, warehouse.getLocation());
            statement.setLong(3, warehouse.getWarehouseID());
            statement.executeUpdate();
        }
    }

    public void deleteWarehouse(Long id) throws SQLException {
        String sql = "DELETE FROM warehouse WHERE warehouseID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}
