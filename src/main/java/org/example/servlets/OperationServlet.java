package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.di.ApplicationContext;
import org.example.model.Operation;
import org.example.service.OperationServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/operations")
public class OperationServlet extends HttpServlet {

    private OperationServiceImpl operationService;

    @Override
    public void init() throws ServletException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("appContext");
        if (context != null) {
            this.operationService = context.getOperationService();
        } else {
            throw new ServletException("ApplicationContext is not initialized.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Operation> operations = null;
        try {
            operations = operationService.getAllOperations();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("operations", operations);
        request.getRequestDispatcher("/operations.jsp").forward(request, response);
    }
}
