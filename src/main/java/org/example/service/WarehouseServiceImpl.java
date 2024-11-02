package org.example.service;

import org.example.di.ApplicationContext;
import org.example.model.Warehouse;
import org.example.interfaces.WarehouseRepository;
import org.example.interfaces.WarehouseService;

import java.sql.SQLException;
import java.util.List;

public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl(ApplicationContext context) {
        this.warehouseRepository = context.getWarehouseRepository();
    }

    @Override
    public void addWarehouse(Warehouse warehouse) throws SQLException {
        if (warehouse.getName() == null || warehouse.getName().isEmpty()) {
            throw new IllegalArgumentException("Warehouse name cannot be null or empty.");
        }
        warehouseRepository.create(warehouse);
    }

    @Override
    public Warehouse getWarehouseById(Long id) throws SQLException {
        Warehouse warehouse = warehouseRepository.findById(id);
        if (warehouse == null) {
            throw new SQLException("Warehouse not found with ID: " + id);
        }
        return warehouse;
    }

    @Override
    public List<Warehouse> getAllWarehouses() throws SQLException {
        return warehouseRepository.findAll();
    }

    @Override
    public void updateWarehouse(Warehouse warehouse) throws SQLException {
        if (warehouse.getWarehouseID() == null || warehouse.getWarehouseID() <= 0) {
            throw new IllegalArgumentException("Warehouse ID must be positive.");
        }
        warehouseRepository.update(warehouse);
    }

    @Override
    public void deleteWarehouse(Long id) throws SQLException {
        Warehouse warehouse = getWarehouseById(id);
        if (warehouse == null) {
            throw new SQLException("Cannot delete: Warehouse not found with ID: " + id);
        }
        warehouseRepository.delete(id);
    }
}
