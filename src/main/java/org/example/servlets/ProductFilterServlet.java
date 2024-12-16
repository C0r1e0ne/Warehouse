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
import java.util.List;

@WebServlet("/product-filter")
public class ProductFilterServlet extends HttpServlet {

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
            String priceParam = request.getParameter("price");
            String quantityParam = request.getParameter("quantity");
            String pageParam = request.getParameter("page");

            boolean hasFilters = (priceParam != null && !priceParam.isEmpty()) || (quantityParam != null && !quantityParam.isEmpty());

            if (hasFilters) {
                double price = (priceParam != null && !priceParam.isEmpty()) ? Double.parseDouble(priceParam) : Double.MAX_VALUE;
                int quantity = (quantityParam != null && !quantityParam.isEmpty()) ? Integer.parseInt(quantityParam) : 0;
                int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
                int pageSize = 10;

                List<Product> products = productService.filterProducts(price, quantity, page, pageSize);

                int totalProductCount = productService.getTotalProductCount(price, quantity);
                int totalPages = (int) Math.ceil((double) totalProductCount / pageSize);

                request.setAttribute("products", products);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
            }

            request.getRequestDispatcher("/product-filter.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
        }
    }
}
