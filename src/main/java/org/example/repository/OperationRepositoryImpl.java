package org.example.repository;

import org.example.interfaces.OperationRepository;
import org.example.model.Operation;
import org.example.connection.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperationRepositoryImpl implements OperationRepository {

    @Override
    public void create(Operation operation) throws SQLException {
        String sql = "INSERT INTO operation (operationType) VALUES (?::operation_type)";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, operation.getOperationType().name());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        operation.setOperationID(generatedKeys.getLong(1));
                    }
                }
            }
        }
    }

    @Override
    public Operation findById(Long id) throws SQLException {
        String sql = "SELECT * FROM operation WHERE operationid = ?";
        Operation operation = null;

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                operation = new Operation();
                operation.setOperationID(rs.getLong("operationid"));
                operation.setOperationType(Operation.OperationType.valueOf(rs.getString("operationType")));
            }
        }
        return operation;
    }

    @Override
    public List<Operation> findAll() throws SQLException {
        String sql = "SELECT * FROM operation";
        List<Operation> operations = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Operation operation = new Operation();
                operation.setOperationID(rs.getLong("operationid"));
                operation.setOperationType(Operation.OperationType.valueOf(rs.getString("operationType")));
                operations.add(operation);
            }
        }
        return operations;
    }

    @Override
    public void update(Operation operation) throws SQLException {
        String sql = "UPDATE operation SET operationType = ? WHERE operationid = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, operation.getOperationType().name());
            stmt.setLong(2, operation.getOperationID());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM operation WHERE operationid = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
