package org.example.interfaces;

import java.sql.SQLException;

public interface StatisticsService {
    int getOperationsCount() throws SQLException;
    int getUsersCount() throws SQLException;
}
