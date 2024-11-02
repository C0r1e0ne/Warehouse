package org.example.interfaces;

import org.example.model.Operation;

import java.sql.SQLException;
import java.util.List;

public interface OperationRepository {
    void create(Operation operation) throws SQLException;
    Operation findById(Long id) throws SQLException;
    List<Operation> findAll() throws SQLException;
    void update(Operation operation) throws SQLException;
    void delete(Long id) throws SQLException;
}
