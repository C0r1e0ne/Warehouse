package org.example.interfaces;

import org.example.model.Warehouse;

import java.sql.SQLException;
import java.util.List;

public interface WarehouseRepository {
    void create(Warehouse warehouse) throws SQLException;
    Warehouse findById(Long id) throws SQLException;
    List<Warehouse> findAll() throws SQLException;
    void update(Warehouse warehouse) throws SQLException;
    void delete(Long id) throws SQLException;
}
