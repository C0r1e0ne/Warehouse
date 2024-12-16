package org.example.service;

import org.example.di.ApplicationContext;
import org.example.interfaces.OperationRepository;
import org.example.interfaces.StatisticsService;
import org.example.interfaces.UserRepository;

import java.sql.SQLException;

public class StatisticsServiceImpl implements StatisticsService {

    private final OperationRepository operationRepository;
    private final UserRepository userRepository;

    public StatisticsServiceImpl(ApplicationContext context) {
        this.operationRepository = context.getOperationRepository();
        this.userRepository = context.getUserRepository();
    }

    @Override
    public int getOperationsCount() throws SQLException {
        return operationRepository.countAllRows();
    }

    @Override
    public int getUsersCount() throws SQLException {
        return userRepository.countAllRows();
    }
}
