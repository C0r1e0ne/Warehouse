package org.example.servlets;

import org.example.di.ApplicationContext;
import org.example.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.ProductServiceImpl;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/edit-product")
public class EditProductServlet extends HttpServlet {

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam != null) {
            try {
                Long id = Long.parseLong(idParam);
                Product product = productService.getProductById(id);
                if (product != null) {
                    req.setAttribute("product", product);
                    req.getRequestDispatcher("edit-product.jsp").forward(req, resp);
                    return;
                }
            } catch (NumberFormatException | SQLException e) {
                e.printStackTrace();
            }
        }
        req.setAttribute("error", "Product not found.");
        resp.sendRedirect("products");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        String name = req.getParameter("name");
        String priceParam = req.getParameter("price");
        String quantityParam = req.getParameter("quantity");

        try {
            Long id = Long.parseLong(idParam);
            double price = Double.parseDouble(priceParam);
            double quantity = Double.parseDouble(quantityParam);

            Product product = new Product();
            product.setProductID(id);
            product.setName(name);
            product.setPrice(price);
            product.setQuantity(quantity);

            productService.updateProduct(product);

            resp.sendRedirect("products");
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Failed to update product. Please try again.");
            doGet(req, resp);
        }
    }
}
