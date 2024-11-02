package org.example.di;

import org.example.interfaces.OperationRepository;
import org.example.interfaces.ProductMovementRepository;
import org.example.interfaces.ProductRepository;
import org.example.interfaces.WarehouseRepository;
import org.example.repository.*;

public class ApplicationContext {

    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final OperationRepository operationRepository;
    private final ProductMovementRepository productMovementRepository;

    public ApplicationContext() {
        // Создаем экземпляры репозиториев и любых других необходимых зависимостей
        this.productRepository = new ProductRepositoryImpl();
        this.warehouseRepository = new WarehouseRepositoryImpl();
        this.operationRepository = new OperationRepositoryImpl();
        this.productMovementRepository = new ProductMovementRepositoryImpl();
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
}
