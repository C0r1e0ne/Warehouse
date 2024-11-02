package org.example.interfaces;

import org.example.model.Warehouse;
import java.sql.SQLException;
import java.util.List;

public interface WarehouseService {
    void addWarehouse(Warehouse warehouse) throws SQLException;
    Warehouse getWarehouseById(Long id) throws SQLException;
    List<Warehouse> getAllWarehouses() throws SQLException;
    void updateWarehouse(Warehouse warehouse) throws SQLException;
    void deleteWarehouse(Long id) throws SQLException;
}