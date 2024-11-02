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
        if (product.getPrice() < 0 || product.getQuantity() < 0) {
            throw new IllegalArgumentException("Цена и количество должны быть неотрицательными");
        }
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
}
