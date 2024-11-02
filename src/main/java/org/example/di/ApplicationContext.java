package org.example.di;

import org.example.repository.*;
import org.example.service.*;
import org.example.interfaces.*;

public class ApplicationContext {
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final OperationRepository operationRepository;
    private final ProductMovementRepository productMovementRepository;
    private final UserRepository userRepository;

    private final ProductService productService;
    private final WarehouseService warehouseService;
    private final OperationService operationService;
    private final ProductMovementService productMovementService;
    private final UserService userService;

    public ApplicationContext() {
        this.productRepository = new ProductRepositoryImpl();
        this.warehouseRepository = new WarehouseRepositoryImpl();
        this.operationRepository = new OperationRepositoryImpl();
        this.productMovementRepository = new ProductMovementRepositoryImpl();
        this.userRepository = new UserRepositoryImpl();

        this.productService = new ProductServiceImpl(this);
        this.warehouseService = new WarehouseServiceImpl(this);
        this.operationService = new OperationServiceImpl(this);
        this.productMovementService = new ProductMovementServiceImpl(this);
        this.userService = new UserServiceImpl(this);
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public WarehouseRepository getWarehouseRepository() {
        return warehouseRepository;
    }

    public OperationRepository getOperationRepository() {
        return operationRepository;
    }

    public ProductMovementRepository getProductMovementRepository() {
        return productMovementRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public ProductService getProductService() {
        return productService;
    }

    public WarehouseService getWarehouseService() {
        return warehouseService;
    }

    public OperationService getOperationService() {
        return operationService;
    }

    public ProductMovementService getProductMovementService() {
        return productMovementService;
    }

    public UserService getUserService() {
        return userService;
    }
}
