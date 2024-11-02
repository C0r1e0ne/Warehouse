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
}
