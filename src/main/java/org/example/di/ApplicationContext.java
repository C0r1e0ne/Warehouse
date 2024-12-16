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

    private final ProductServiceImpl productService;
    private final WarehouseServiceImpl warehouseService;
    private final OperationServiceImpl operationService;
    private final ProductMovementServiceImpl productMovementService;
    private final UserServiceImpl userService;
    private final StatisticsServiceImpl statisticsService;

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
        this.statisticsService = new StatisticsServiceImpl(this);

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

    public ProductServiceImpl getProductService() {
        return productService;
    }

    public WarehouseServiceImpl getWarehouseService() {
        return warehouseService;
    }

    public OperationServiceImpl getOperationService() {
        return operationService;
    }

    public ProductMovementServiceImpl getProductMovementService() {
        return productMovementService;
    }

    public UserServiceImpl getUserService() {
        return userService;
    }

    public StatisticsServiceImpl getStatisticsService() {
        return statisticsService;  // Возвращаем новый экземпляр StatisticsService
    }
}
