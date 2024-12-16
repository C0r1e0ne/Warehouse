package org.example.repository;

import org.example.interfaces.WarehouseRepository;
import org.example.model.Warehouse;
import org.example.connection.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarehouseRepositoryImpl implements WarehouseRepository {

    @Override
    public void create(Warehouse warehouse) throws SQLException {
        String sql = "INSERT INTO warehouse (name, location) VALUES (?, ?)";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, warehouse.getName());
            stmt.setString(2, warehouse.getLocation());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        warehouse.setWarehouseID(generatedKeys.getLong(1));
                    } else {
                        throw new SQLException("Creating warehouse failed, no ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Creating warehouse failed, no rows affected.");
            }
        }
    }

    @Override
    public Warehouse findById(Long id) throws SQLException {
        String sql = "SELECT * FROM warehouse WHERE warehouseID = ?";
        Warehouse warehouse = null;

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                warehouse = new Warehouse();
                warehouse.setWarehouseID(rs.getLong("warehouseID"));
                warehouse.setName(rs.getString("name"));
                warehouse.setLocation(rs.getString("location"));
            }
        }
        return warehouse;
    }

    @Override
    public List<Warehouse> findAll() throws SQLException {
        String sql = "SELECT * FROM warehouse";
        List<Warehouse> warehouses = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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

    @Override
    public void update(Warehouse warehouse) throws SQLException {
        String sql = "UPDATE warehouse SET name = ?, location = ? WHERE warehouseID = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, warehouse.getName());
            stmt.setString(2, warehouse.getLocation());
            stmt.setLong(3, warehouse.getWarehouseID());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM warehouse WHERE warehouseID = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
    @Override
    public List<Warehouse> findPaginated(int page, int size) throws SQLException {
        String sql = "SELECT * FROM warehouse ORDER BY warehouseID ASC LIMIT ? OFFSET ?";
        List<Warehouse> warehouses = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, size);
            stmt.setInt(2, (page - 1) * size); // OFFSET для пагинации

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Warehouse warehouse = new Warehouse();
                    warehouse.setWarehouseID(rs.getLong("warehouseID"));
                    warehouse.setName(rs.getString("name"));
                    warehouse.setLocation(rs.getString("location"));
                    warehouses.add(warehouse);
                }
            }
        }
        return warehouses;
    }

    @Override
    public int getTotalCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM warehouse";
        int totalCount = 0;

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
        }
        return totalCount;
    }

}
