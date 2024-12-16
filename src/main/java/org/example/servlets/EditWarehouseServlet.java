package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.di.ApplicationContext;
import org.example.interfaces.WarehouseService;
import org.example.model.Warehouse;
import org.example.service.WarehouseServiceImpl;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/edit-warehouse")
public class EditWarehouseServlet extends HttpServlet {

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
        String warehouseIdParam = request.getParameter("id");

        if (warehouseIdParam != null) {
            try {
                Long warehouseId = Long.valueOf(warehouseIdParam);
                Warehouse warehouse = warehouseService.getWarehouseById(warehouseId);

                request.setAttribute("warehouse", warehouse);
                request.getRequestDispatcher("/edit-warehouse.jsp").forward(request, response);

            } catch (NumberFormatException e) {
                handleError(request, response, "Invalid warehouse ID format.");
            } catch (SQLException e) {
                handleError(request, response, "Error retrieving warehouse: " + e.getMessage());
            }
        } else {
            handleError(request, response, "Warehouse ID is missing.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String warehouseIdParam = request.getParameter("id");
        String name = request.getParameter("name");
        String location = request.getParameter("location");

        try {
            if (warehouseIdParam != null) {
                Long warehouseId = Long.valueOf(warehouseIdParam);
                Warehouse warehouse = new Warehouse();
                warehouse.setWarehouseID(warehouseId);
                warehouse.setName(name);
                warehouse.setLocation(location);
                warehouseService.updateWarehouse(warehouse);
                response.sendRedirect("warehouses");
            }
        } catch (SQLException e) {
            handleError(request, response, "Error updating warehouse: " + e.getMessage());
        }
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {
        request.setAttribute("error", errorMessage);
        request.getRequestDispatcher("/warehouses").forward(request, response);
    }
}

