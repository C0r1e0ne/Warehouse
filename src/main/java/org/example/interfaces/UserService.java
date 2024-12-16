package org.example.interfaces;

import org.example.model.Users;
import java.sql.SQLException;
import java.util.List;

public interface UserService {
    void addUser(Users user) throws SQLException;
    Users getUserById(Long id) throws SQLException;
    List<Users> getAllUsers() throws SQLException;
    void updateUser(Users user) throws SQLException;
    void deleteUser(Long id) throws SQLException;
    Users getUserByLogin(String login) throws SQLException;
    List<Users> getUsersPaginated(int page, int recordsPerPage) throws SQLException;
    int getTotalUserCount() throws SQLException;
    List<Users> searchUsers(String query, int page, int pageSize) throws SQLException;
    int getTotalUserCount(String query, String filter) throws SQLException;

}
