package org.example.interfaces;

import org.example.model.Users;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    void create(Users user) throws SQLException;
    Users findById(Long id) throws SQLException;
    List<Users> findAll() throws SQLException;
    void update(Users user) throws SQLException;
    void delete(Long id) throws SQLException;
    int countAllRows() throws SQLException;
    List<Users> findUsersWithLimitAndOffset(int limit, int offset) throws SQLException;
    int getUserCount() throws SQLException;
    List<Users> searchUsers(String searchTerm, int offset, int limit) throws SQLException;
    int countFilteredUsers(String query, String filter) throws SQLException;
    List<Users> findUsersPaginated(int offset, int limit) throws SQLException;
}
