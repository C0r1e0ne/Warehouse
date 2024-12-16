package org.example.interfaces;

import org.example.model.Operation;
import java.sql.SQLException;
import java.util.List;

public interface OperationService {
    void addOperation(Operation operation) throws SQLException;
    Operation getOperationById(Long id) throws SQLException;
    List<Operation> getAllOperations() throws SQLException;
    void updateOperation(Operation operation) throws SQLException;
    void deleteOperation(Long id) throws SQLException;
    List<Operation> getOperationsPaginated(int page, int size) throws SQLException;
    int countOperations() throws SQLException;
}
