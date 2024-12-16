package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.di.ApplicationContext;
import org.example.model.Users;
import org.example.service.UserServiceImpl;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserServiceImpl userService;

    @Override
    public void init() throws ServletException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("appContext");
        if (context != null) {
            this.userService = context.getUserService();
        } else {
            throw new ServletException("ApplicationContext is not initialized.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Users user = authenticate(login, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("username", user.getLogin());

                response.sendRedirect("home.jsp");
            } else {
                response.sendRedirect("index.jsp?error=true");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error during authentication.", e);
        }
    }

    private Users authenticate(String login, String password) throws SQLException {
        Users user = userService.getUserByLogin(login);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}