package org.example.servlets;

import org.example.di.ApplicationContext;
import org.example.model.Product;
import org.example.service.ProductServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {

    private ProductServiceImpl productService;

    @Override
    public void init() throws ServletException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("appContext");
        if (context != null) {
            this.productService = context.getProductService();
        } else {
            throw new ServletException("ApplicationContext is not initialized.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int page = parseIntOrDefault(request.getParameter("page"), 1);
            int size = 10;

            String action = request.getParameter("action");
            String productIdParam = request.getParameter("id");
            if ("delete".equalsIgnoreCase(action) && productIdParam != null) {
                productService.deleteProduct(Long.parseLong(productIdParam));
                response.sendRedirect("products");
                return;
            }

            List<Product> products = productService.getProductsPaginated(page, size);
            int totalProducts = productService.getTotalProductCount();
            int totalPages = (int) Math.ceil((double) totalProducts / size);

            request.setAttribute("products", products);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.getRequestDispatcher("/products.jsp").forward(request, response);
        } catch (SQLException | NumberFormatException e) {
            handleError(request, response, "Ошибка при получении продуктов: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Создание продукта
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            double quantity = Double.parseDouble(request.getParameter("quantity"));

            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setQuantity(quantity);
            productService.addProduct(product);
            response.sendRedirect("products");
        } catch (SQLException e) {
            handleError(request, response, "Ошибка при добавлении продукта: " + e.getMessage());
        } catch (NumberFormatException e) {
            handleError(request, response, "Некорректный формат данных.");
        }
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute("error", message);
        request.getRequestDispatcher("/products.jsp").forward(request, response);
    }

    private int parseIntOrDefault(String param, int defaultValue) {
        try {
            return param != null ? Integer.parseInt(param) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
