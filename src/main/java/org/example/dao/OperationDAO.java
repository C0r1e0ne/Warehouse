package org.example.dao;

import org.example.model.Operation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperationDAO {
    public void createOperation(Operation operation) throws SQLException {
        String query = "INSERT INTO Operation (operationType) VALUES (?::operation_type)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, operation.getOperationType().toString());

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

    public Operation getOperationById(Long operationId) throws SQLException {
        String query = "SELECT * FROM Operation WHERE operationID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, operationId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Operation operation = new Operation();
                    operation.setOperationID(rs.getLong("operationID"));
                    operation.setOperationType(Operation.OperationType.valueOf(rs.getString("operationType")));
                    return operation;
                }
            }
        }
        return null;
    }

    public void updateOperation(Operation operation) throws SQLException {
        String query = "UPDATE Operation SET operationType = ? WHERE operationID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, operation.getOperationType().toString());
            stmt.setLong(2, operation.getOperationID());
            stmt.executeUpdate();
        }
    }

    public void deleteOperation(Long operationId) throws SQLException {
        String query = "DELETE FROM Operation WHERE operationID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, operationId);
            stmt.executeUpdate();
        }
    }

    public List<Operation> getAllOperations() throws SQLException {
        String query = "SELECT * FROM Operation";
        List<Operation> operations = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Operation operation = new Operation();
                operation.setOperationID(rs.getLong("operationID"));
                operation.setOperationType(Operation.OperationType.valueOf(rs.getString("operationType")));
                operations.add(operation);
            }
        }
        return operations;
    }
}
