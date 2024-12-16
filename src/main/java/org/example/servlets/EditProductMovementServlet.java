package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.di.ApplicationContext;
import org.example.model.ProductMovement;
import org.example.service.ProductMovementServiceImpl;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/edit-product-movement")
public class EditProductMovementServlet extends HttpServlet {

    private ProductMovementServiceImpl productMovementService;

    @Override
    public void init() throws ServletException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("appContext");
        if (context != null) {
            this.productMovementService = context.getProductMovementService();
        } else {
            throw new ServletException("ApplicationContext is not initialized.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String movementIDParam = request.getParameter("id");

        if (movementIDParam != null) {
            try {
                Long movementID = Long.valueOf(movementIDParam);
                ProductMovement pm = productMovementService.getProductMovementById(movementID);

                if (pm != null) {
                    request.setAttribute("productMovement", pm);
                    request.getRequestDispatcher("/edit-product-movement.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Product movement not found.");
                    request.getRequestDispatcher("/product-movement").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid product movement ID format.");
                request.getRequestDispatcher("/product-movement").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException("Error retrieving product movement.", e);
            }
        } else {
            request.setAttribute("error", "Product movement ID is missing.");
            request.getRequestDispatcher("/product-movement").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String movementIDParam = request.getParameter("id");

        if (movementIDParam == null || movementIDParam.trim().isEmpty()) {
            request.setAttribute("error", "Product movement ID is missing.");
            request.getRequestDispatcher("/edit-product-movement.jsp").forward(request, response);
            return;
        }

        try {
            Long movementID = Long.valueOf(movementIDParam);
            ProductMovement pm = productMovementService.getProductMovementById(movementID);

            if (pm != null) {
                String productID = request.getParameter("productID");
                String warehouseID = request.getParameter("warehouseID");
                String operationID = request.getParameter("operationID");
                String quantity = request.getParameter("quantity");
                String date = request.getParameter("date");

                pm.setProductID(Long.parseLong(productID));
                pm.setWarehouseID(Long.parseLong(warehouseID));
                pm.setOperationID(Long.parseLong(operationID));
                pm.setQuantity(Double.parseDouble(quantity));
                pm.setDate(java.time.LocalDate.parse(date));

                productMovementService.updateProductMovement(pm);
                response.sendRedirect("product-movement");
            } else {
                request.setAttribute("error", "Product movement not found.");
                request.getRequestDispatcher("/edit-product-movement.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid input format.");
            request.getRequestDispatcher("/edit-product-movement.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Error updating product movement.");
            request.getRequestDispatcher("/edit-product-movement.jsp").forward(request, response);
        }
    }
}
