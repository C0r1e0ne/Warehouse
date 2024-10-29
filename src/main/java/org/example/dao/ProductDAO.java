package org.example.dao;

import org.example.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public void createProduct(Product product) throws SQLException {
        String sql = "INSERT INTO product (name, price, quantity) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setDouble(3, product.getQuantity());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        product.setProductID(generatedKeys.getLong(1));
                    } else {
                        throw new SQLException("Создание продукта завершилось неудачно, ID не был получен.");
                    }
                }
            } else {
                throw new SQLException("Создание продукта завершилось неудачно, ни одна строка не была затронута.");
            }
        }
    }

    public Product getProductById(Long id) throws SQLException {
        String sql = "SELECT * FROM product WHERE productid = ?";
        Product product = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                product = new Product();
                product.setProductID(resultSet.getLong("productid"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setQuantity(resultSet.getDouble("quantity"));
            }
        }

        return product;
    }

    public List<Product> getAllProducts() throws SQLException {
        String sql = "SELECT * FROM product";
        List<Product> products = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Product product = new Product();
                product.setProductID(resultSet.getLong("productid"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setQuantity(resultSet.getDouble("quantity"));
                products.add(product);
            }
        }

        return products;
    }

    public void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE product SET name = ?, price = ?, quantity = ? WHERE productid = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setDouble(3, product.getQuantity());
            statement.setLong(4, product.getProductID());

            statement.executeUpdate();
        }
    }

    public void deleteProduct(Long id) throws SQLException {
        String sql = "DELETE FROM product WHERE productid = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            statement.executeUpdate();
        }
    }
}
