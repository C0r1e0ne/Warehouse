package org.example.repository;

import org.example.interfaces.UserRepository;
import org.example.model.Users;
import org.example.connection.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public void create(Users user) throws SQLException {
        String sql = "INSERT INTO users (name, login, password) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getPassword());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getLong(1));
                    }
                }
            }
        }
    }

    @Override
    public Users findById(Long id) throws SQLException {
        String sql = "SELECT * FROM users WHERE userid = ?";
        Users user = null;

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new Users();
                user.setId(rs.getLong("userid"));
                user.setName(rs.getString("name"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
            }
        }
        return user;
    }

    @Override
    public List<Users> findAll() throws SQLException {
        String sql = "SELECT * FROM users";
        List<Users> users = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Users user = new Users();
                user.setId(rs.getLong("userid"));
                user.setName(rs.getString("name"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public void update(Users user) throws SQLException {
        String sql = "UPDATE users SET name = ?, login = ?, password = ? WHERE userid = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getPassword());
            stmt.setLong(4, user.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM users WHERE userid = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
