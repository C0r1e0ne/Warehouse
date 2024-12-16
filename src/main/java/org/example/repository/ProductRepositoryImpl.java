package org.example.repository;

import org.example.interfaces.ProductRepository;
import org.example.model.Product;
import org.example.connection.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public void create(Product product) throws SQLException {
        String sql = "INSERT INTO product (name, price, quantity) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setDouble(3, product.getQuantity());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        product.setProductID(generatedKeys.getLong(1));
                    }
                }
            }
        }
    }

    @Override
    public Product findById(Long id) throws SQLException {
        String sql = "SELECT * FROM product WHERE productid = ?";
        Product product = null;

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                product = new Product();
                product.setProductID(rs.getLong("productid"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getDouble("quantity"));
            }
        }
        return product;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        String sql = "SELECT * FROM product";
        List<Product> products = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product();
                product.setProductID(rs.getLong("productid"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getDouble("quantity"));
                products.add(product);
            }
        }
        return products;
    }

    @Override
    public void update(Product product) throws SQLException {
        String sql = "UPDATE product SET name = ?, price = ?, quantity = ? WHERE productid = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setDouble(3, product.getQuantity());
            stmt.setLong(4, product.getProductID());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM product WHERE productid = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public int getTotalProductCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM product";
        try (Connection connection = ConnectionPool.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    // Новый метод для получения продуктов с пагинацией
    @Override
    public List<Product> getProductsPaginated(int page, int size) throws SQLException {
        int offset = (page - 1) * size;
        String sql = "SELECT * FROM product LIMIT ? OFFSET ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, size);
            stmt.setInt(2, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Product> products = new ArrayList<>();
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductID(rs.getLong("productid"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setQuantity(rs.getDouble("quantity"));
                    products.add(product);
                }
                return products;
            }
        }
    }
    @Override
    public List<Product> filterProducts(double price, int quantity, int page, int size) throws SQLException {
        int offset = (page - 1) * size;
        String sql = "SELECT * FROM product WHERE price <= ? AND quantity >= ? LIMIT ? OFFSET ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setDouble(1, price);
            stmt.setInt(2, quantity);
            stmt.setInt(3, size);
            stmt.setInt(4, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Product> products = new ArrayList<>();
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductID(rs.getLong("productid"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setQuantity(rs.getDouble("quantity"));
                    products.add(product);
                }
                return products;
            }
        }
    }

    @Override
    public int getFilteredProductCount(double price, int quantity) throws SQLException {
        String sql = "SELECT COUNT(*) FROM product WHERE price <= ? AND quantity >= ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setDouble(1, price);
            stmt.setInt(2, quantity);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

}
