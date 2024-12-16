package org.example.service;

import org.example.di.ApplicationContext;
import org.example.interfaces.ProductRepository;
import org.example.model.Product;
import org.example.interfaces.ProductService;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ApplicationContext context) {
        this.productRepository = context.getProductRepository();
    }

    @Override
    public void addProduct(Product product) throws SQLException {
        validateProduct(product);
        productRepository.create(product);
    }
    @Override
    public Product getProductById(Long id) throws SQLException {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getAllProducts() throws SQLException {
        return productRepository.findAll();
    }

    @Override
    public void updateProduct(Product product) throws SQLException {
        productRepository.update(product);
    }

    @Override
    public void deleteProduct(Long id) throws SQLException {
        productRepository.delete(id);
    }

    @Override
    public int getTotalProductCount() throws SQLException {
        return productRepository.getTotalProductCount();
    }

    // Новый метод для получения продуктов с пагинацией
    @Override
    public List<Product> getProductsPaginated(int page, int size) throws SQLException {
        validatePagination(page, size);
        return productRepository.getProductsPaginated(page, size);
    }
    @Override
    public List<Product> filterProducts(double price, int quantity, int page, int size) throws SQLException {
        return productRepository.filterProducts(price, quantity, page, size);
    }

    @Override
    public int getTotalProductCount(double price, int quantity) throws SQLException {
        return productRepository.getFilteredProductCount(price, quantity);
    }
    private void validateProduct(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Имя продукта не может быть пустым.");
        }
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной.");
        }
        if (product.getQuantity() < 0) {
            throw new IllegalArgumentException("Количество не может быть отрицательным.");
        }
    }

    private void validatePagination(int page, int size) {
        if (page <= 0 || size <= 0) {
            throw new IllegalArgumentException("Страница и размер должны быть положительными числами.");
        }
    }
}
