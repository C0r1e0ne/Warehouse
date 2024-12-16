package org.example.service;

import org.example.di.ApplicationContext;
import org.example.interfaces.ProductMovementRepository;
import org.example.model.ProductMovement;
import org.example.model.Warehouse;
import org.example.interfaces.WarehouseRepository;
import org.example.interfaces.WarehouseService;

import java.sql.SQLException;
import java.util.List;

public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private ProductMovementRepository productMovementRepository;

    public WarehouseServiceImpl(ApplicationContext context) {
        this.warehouseRepository = context.getWarehouseRepository();
        this.productMovementRepository = context.getProductMovementRepository();
    }

    @Override
    public void addWarehouse(Warehouse warehouse) throws SQLException {
        validateWarehouse(warehouse);
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
    public boolean deleteWarehouse(Long warehouseId) throws SQLException {
        List<ProductMovement> movements = productMovementRepository.findByWarehouseId(warehouseId);
        if (!movements.isEmpty()) {
            throw new SQLException("Cannot delete warehouse. It is referenced by other records.");
        }
        warehouseRepository.delete(warehouseId);
        return true;
    }

    @Override
    public List<Warehouse> getWarehousesPaginated(int page, int size) throws SQLException {
        if (page < 1 || size < 1) {
            throw new IllegalArgumentException("Page and size must be positive.");
        }
        return warehouseRepository.findPaginated(page, size);
    }

    @Override
    public int getTotalWarehouseCount() throws SQLException {
        return warehouseRepository.getTotalCount();
    }
    private void validateWarehouse(Warehouse warehouse) {
        if (warehouse.getName() == null || warehouse.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Warehouse name cannot be empty.");
        }
        if (warehouse.getLocation() == null || warehouse.getLocation().trim().isEmpty()) {
            throw new IllegalArgumentException("Warehouse location cannot be empty.");
        }
    }
}
