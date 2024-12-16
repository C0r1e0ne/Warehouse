package org.example.service;

import org.example.di.ApplicationContext;
import org.example.model.Operation;
import org.example.interfaces.OperationRepository;
import org.example.interfaces.OperationService;

import java.sql.SQLException;
import java.util.List;

public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;

    public OperationServiceImpl(ApplicationContext context) {
        this.operationRepository = context.getOperationRepository();
    }

    @Override
    public void addOperation(Operation operation) throws SQLException {
        if (operation.getOperationType() == null) {
            throw new IllegalArgumentException("Operation type cannot be null.");
        }
        operationRepository.create(operation);
    }

    @Override
    public Operation getOperationById(Long id) throws SQLException {
        Operation operation = operationRepository.findById(id);
        if (operation == null) {
            throw new SQLException("Operation not found with ID: " + id);
        }
        return operation;
    }

    @Override
    public List<Operation> getAllOperations() throws SQLException {
        return operationRepository.findAll();
    }

    @Override
    public void updateOperation(Operation operation) throws SQLException {
        if (operation.getOperationID() == null) {
            throw new IllegalArgumentException("Operation ID cannot be null.");
        }
        operationRepository.update(operation);
    }

    @Override
    public void deleteOperation(Long id) throws SQLException {
        Operation operation = getOperationById(id);
        if (operation == null) {
            throw new SQLException("Cannot delete: Operation not found with ID: " + id);
        }
        operationRepository.delete(id);
    }

    @Override
    public List<Operation> getOperationsPaginated(int page, int size) throws SQLException {
        int offset = (page - 1) * size;
        return operationRepository.findPaginated(offset, size);
    }

    @Override
    public int countOperations() throws SQLException {
        return operationRepository.countAllRows();
    }

}
