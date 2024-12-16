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

    @Override
    public int countAllRows() throws SQLException {
        String sql = "SELECT COUNT(*) FROM users";
        int count = 0;

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }
        }
        return count;
    }
    @Override
    public List<Users> findUsersWithLimitAndOffset(int limit, int offset) throws SQLException {
        String query = "SELECT * FROM users LIMIT ? OFFSET ?";
        List<Users> users = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, limit);
            statement.setInt(2, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Users user = new Users();
                    user.setId(resultSet.getLong("userid"));
                    user.setName(resultSet.getString("name"));
                    user.setLogin(resultSet.getString("login"));
                    user.setPassword(resultSet.getString("password"));
                    users.add(user);
                }
            }
        }
        return users;
    }

    @Override
    public int getUserCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM users";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return 0;
    }
    public List<Users> searchUsers(String searchTerm, int offset, int limit) throws SQLException {
        if (offset < 0) offset = 0;
        String sql = "SELECT * FROM users WHERE name LIKE ? OR login LIKE ? LIMIT ? OFFSET ?";
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchTerm + "%");
            stmt.setString(2, "%" + searchTerm + "%");
            stmt.setInt(3, limit);
            stmt.setInt(4, offset);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Users> users = new ArrayList<>();
                while (rs.next()) {
                    Users user = new Users();
                    user.setId(rs.getLong("userid"));
                    user.setName(rs.getString("name"));
                    user.setLogin(rs.getString("login"));
                    users.add(user);
                }
                return users;
            }
        }
    }



    @Override
    public int countFilteredUsers(String query, String filter) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE " + filter + " ILIKE ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + query + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        return 0;
    }
    private Users mapRowToUser(ResultSet resultSet) throws SQLException {
        Users user = new Users();
        user.setId(resultSet.getLong("userid"));
        user.setName(resultSet.getString("name"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }

    @Override
    public List<Users> findUsersPaginated(int offset, int limit) throws SQLException {
        String sql = "SELECT * FROM users LIMIT ? OFFSET ?";
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();
            List<Users> users = new ArrayList<>();
            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }
            return users;
        }
    }


}
