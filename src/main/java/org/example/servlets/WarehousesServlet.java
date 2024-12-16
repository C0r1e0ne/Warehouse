package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.di.ApplicationContext;
import org.example.interfaces.WarehouseService;
import org.example.model.Warehouse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/warehouses")
public class WarehousesServlet extends HttpServlet {

    private WarehouseService warehouseService;

    @Override
    public void init() throws ServletException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("appContext");
        if (context != null) {
            this.warehouseService = context.getWarehouseService();
        } else {
            throw new ServletException("ApplicationContext is not initialized.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageParam = request.getParameter("page");
        int page = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
        int size = 10;

        String action = request.getParameter("action");
        String idParam = request.getParameter("id");

        try {
            if ("delete".equalsIgnoreCase(action) && idParam != null) {
                Long warehouseId = Long.valueOf(idParam);
                warehouseService.deleteWarehouse(warehouseId);
                response.sendRedirect("warehouses");
                return;
            }

            List<Warehouse> warehouses = warehouseService.getWarehousesPaginated(page, size);
            int totalWarehouses = warehouseService.getTotalWarehouseCount();
            int totalPages = (int) Math.ceil((double) totalWarehouses / size);

            request.setAttribute("warehouses", warehouses);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.getRequestDispatcher("/warehouses.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid ID format.");
            request.getRequestDispatcher("/warehouses.jsp").forward(request, response);
        } catch (SQLException e) {
            handleSqlException(request, response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("delete".equalsIgnoreCase(action)) {
                Long id = Long.valueOf(request.getParameter("id"));
                warehouseService.deleteWarehouse(id);
                response.sendRedirect("warehouses");
                return;
            }

            Warehouse warehouse = new Warehouse();
            warehouse.setName(request.getParameter("name"));
            warehouse.setLocation(request.getParameter("location"));

            warehouseService.addWarehouse(warehouse);
            response.sendRedirect("warehouses");
        } catch (SQLException e) {
            handleSqlException(request, response, e);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/warehouses.jsp").forward(request, response);
        }
    }

    private void handleSqlException(HttpServletRequest request, HttpServletResponse response, SQLException e) throws ServletException, IOException {
        if (e.getMessage().contains("violates foreign key constraint")) {
            request.setAttribute("error", "Cannot delete warehouse. It is referenced by other records.");
        } else {
            request.setAttribute("error", "Error occurred while processing the request.");
        }
        request.getRequestDispatcher("/warehouses.jsp").forward(request, response);
    }
}
