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
}
