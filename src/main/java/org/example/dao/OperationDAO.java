package org.example.dao;

import org.example.model.Operation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperationDAO {

    public void createOperation(Operation operation) throws SQLException {
        String sql = "INSERT INTO operation (operationType) VALUES (?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, operation.getOperationType());
            statement.executeUpdate();
        }
    }

    public Operation getOperationById(Long id) throws SQLException {
        String sql = "SELECT * FROM operation WHERE operationID = ?";
        Operation operation = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                operation = new Operation();
                operation.setOperationID(resultSet.getLong("operationID"));
                operation.setOperationType(resultSet.getString("operationType"));
            }
        }

        return operation;
    }

    public List<Operation> getAllOperations() throws SQLException {
        String sql = "SELECT * FROM operation";
        List<Operation> operations = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Operation operation = new Operation();
                operation.setOperationID(resultSet.getLong("operationID"));
                operation.setOperationType(resultSet.getString("operationType"));
                operations.add(operation);
            }
        }

        return operations;
    }

    public void updateOperation(Operation operation) throws SQLException {
        String sql = "UPDATE operation SET operationType = ? WHERE operationID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, operation.getOperationType());
            statement.setLong(2, operation.getOperationID());
            statement.executeUpdate();
        }
    }

    public void deleteOperation(Long id) throws SQLException {
        String sql = "DELETE FROM operation WHERE operationID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}
