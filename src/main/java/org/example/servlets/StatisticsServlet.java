package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.di.ApplicationContext;
import org.example.interfaces.StatisticsService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/statistics")
public class StatisticsServlet extends HttpServlet {

    private StatisticsService statisticsService;

    @Override
    public void init() throws ServletException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("appContext");
        if (context != null) {
            this.statisticsService = context.getStatisticsService();

        } else {
            throw new ServletException("ApplicationContext is not initialized.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int operationsCount = statisticsService.getOperationsCount();
            int usersCount = statisticsService.getUsersCount();
            request.setAttribute("operationsCount", operationsCount);
            request.setAttribute("usersCount", usersCount);


            request.getRequestDispatcher("statistics.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error while fetching statistics", e);
        }
    }
}
