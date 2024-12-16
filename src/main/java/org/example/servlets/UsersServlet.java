package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.di.ApplicationContext;
import org.example.interfaces.UserService;
import org.example.model.Users;
import org.example.service.UserServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    private UserService userService;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchTerm = request.getParameter("search");
        String pageParam = request.getParameter("page");
        int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
        page = Math.max(page, 1);
        int size = 10;

        try {
            List<Users> users;
            if (searchTerm != null && !searchTerm.isEmpty()) {
                users = userService.searchUsers(searchTerm, (page - 1) * size, size); // передаем поисковую строку, offset и limit
            } else {
                users = userService.getUsersPaginated(page, size);
            }

            int totalUsers = userService.getTotalUserCount();
            int totalPages = (int) Math.ceil((double) totalUsers / size);

            request.setAttribute("users", users);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);
            request.setAttribute("searchTerm", searchTerm);

            request.getRequestDispatcher("/users.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error retrieving users", e);
        }
    }

}