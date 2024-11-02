package org.example.service;

import org.example.di.ApplicationContext;
import org.example.model.Users;
import org.example.interfaces.UserRepository;
import org.example.interfaces.UserService;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(ApplicationContext context) {
        this.userRepository = context.getUserRepository();
    }

    @Override
    public void addUser(Users user) throws SQLException {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty.");
        }
        userRepository.create(user);
    }

    @Override
    public Users getUserById(Long id) throws SQLException {
        Users user = userRepository.findById(id);
        if (user == null) {
            throw new SQLException("User not found with ID: " + id);
        }
        return user;
    }

    @Override
    public List<Users> getAllUsers() throws SQLException {
        return userRepository.findAll();
    }

    @Override
    public void updateUser(Users user) throws SQLException {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User ID cannot be null.");
        }
        userRepository.update(user);
    }

    @Override
    public void deleteUser(Long id) throws SQLException {
        Users user = getUserById(id);
        if (user == null) {
            throw new SQLException("Cannot delete: User not found with ID: " + id);
        }
        userRepository.delete(id);
    }
}
