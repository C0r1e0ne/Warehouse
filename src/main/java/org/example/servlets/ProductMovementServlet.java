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
import java.time.LocalDate;
import java.util.List;

@WebServlet("/product-movement")
public class ProductMovementServlet extends HttpServlet {

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
        List<ProductMovement> productMovements = null;
        try {
            productMovements = productMovementService.getAllProductMovements();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("productMovements", productMovements);
        request.getRequestDispatcher("/product-movement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String movementIDParam = request.getParameter("id");
        String productIDParam = request.getParameter("productID");
        String warehouseIDParam = request.getParameter("warehouseID");
        String operationIDParam = request.getParameter("operationID");
        String quantityParam = request.getParameter("quantity");
        String dateParam = request.getParameter("date");

        try {
            if (movementIDParam == null || productIDParam == null || warehouseIDParam == null ||
                    operationIDParam == null || quantityParam == null || dateParam == null) {
                throw new IllegalArgumentException("Missing required parameters.");
            }

            Long movementID = Long.parseLong(movementIDParam.trim());
            Long productID = Long.parseLong(productIDParam.trim());
            Long warehouseID = Long.parseLong(warehouseIDParam.trim());
            Long operationID = Long.parseLong(operationIDParam.trim());
            Double quantity = Double.parseDouble(quantityParam.trim());
            LocalDate date = LocalDate.parse(dateParam.trim());

            ProductMovement pm = productMovementService.getProductMovementById(movementID);

            if (pm != null) {
                // Обновляем поля объекта
                pm.setProductID(productID);
                pm.setWarehouseID(warehouseID);
                pm.setOperationID(operationID);
                pm.setQuantity(quantity);
                pm.setDate(date);

                productMovementService.updateProductMovement(pm);

                response.sendRedirect("product-movement");
            } else {
                request.setAttribute("error", "Product movement not found.");
                request.getRequestDispatcher("/edit-product-movement.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid number format: " + e.getMessage());
            request.getRequestDispatcher("/edit-product-movement.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Invalid input: " + e.getMessage());
            request.getRequestDispatcher("/edit-product-movement.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/edit-product-movement.jsp").forward(request, response);
        }
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String movementID = request.getParameter("movementID");
        String productID = request.getParameter("productID");
        String warehouseID = request.getParameter("warehouseID");
        String operationID = request.getParameter("operationID");
        String quantity = request.getParameter("quantity");
        String date = request.getParameter("date");

        try {
            if (movementID != null && productID != null && warehouseID != null && operationID != null && quantity != null && date != null) {
                ProductMovement productMovement = new ProductMovement();
                productMovement.setMovementID(Long.parseLong(movementID));
                productMovement.setProductID(Long.parseLong(productID));
                productMovement.setWarehouseID(Long.parseLong(warehouseID));
                productMovement.setOperationID(Long.parseLong(operationID));
                productMovement.setQuantity(Integer.parseInt(quantity));
                productMovement.setDate(java.time.LocalDate.parse(date));

                productMovementService.updateProductMovement(productMovement);
                response.sendRedirect("product-movement");
            } else {
                request.setAttribute("error", "All fields must be filled.");
                request.getRequestDispatcher("/product-movement.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Error while updating product movement.");
            request.getRequestDispatcher("/product-movement.jsp").forward(request, response);
        }
    }
}