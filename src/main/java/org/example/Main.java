package org.example;

import org.example.interfaces.OperationRepository;
import org.example.interfaces.ProductMovementRepository;
import org.example.interfaces.ProductRepository;
import org.example.interfaces.WarehouseRepository;
import org.example.model.Product;
import org.example.model.Warehouse;
import org.example.model.Operation;
import org.example.model.ProductMovement;
import org.example.repository.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            // Создание репозиториев
            ProductRepository productRepository = new ProductRepositoryImpl();
            WarehouseRepository warehouseRepository = new WarehouseRepositoryImpl();
            OperationRepository operationRepository = new OperationRepositoryImpl();
            ProductMovementRepository productMovementRepository = new ProductMovementRepositoryImpl();

            // Операции с Product
            System.out.println("=== Product Operations ===");

            // 1. Создание нового продукта
            Product newProduct = new Product();
            newProduct.setName("New Product");
            newProduct.setPrice(100.0);
            newProduct.setQuantity(20);
            productRepository.create(newProduct);
            System.out.println("Product created: " + newProduct);

            // 2. Получение продукта по ID
            Product fetchedProduct = productRepository.findById(newProduct.getProductID());
            System.out.println("Fetched Product: " + fetchedProduct);

            // 3. Обновление продукта
            fetchedProduct.setName("Updated Product");
            fetchedProduct.setPrice(150.0);
            productRepository.update(fetchedProduct);
            System.out.println("Product updated: " + fetchedProduct);

            // 4. Получение всех продуктов
            List<Product> products = productRepository.findAll();
            System.out.println("All Products:");
            products.forEach(System.out::println);

            // 5. Удаление продукта
            //productRepository.delete(fetchedProduct.getProductID());
            //System.out.println("Product deleted: " + fetchedProduct.getProductID());

            // Операции с Warehouse
            System.out.println("=== Warehouse Operations ===");

            // 1. Создание склада
            Warehouse newWarehouse = new Warehouse();
            newWarehouse.setName("Main Warehouse");
            newWarehouse.setLocation("Downtown");
            warehouseRepository.create(newWarehouse);
            System.out.println("Warehouse created: " + newWarehouse);

            // 2. Получение склада по ID
            Warehouse fetchedWarehouse = warehouseRepository.findById(newWarehouse.getWarehouseID());
            System.out.println("Fetched Warehouse: " + fetchedWarehouse);

            // Операции с Operation
            System.out.println("=== Operation Operations ===");

            // 1. Создание новой операции
            Operation newOperation = new Operation();
            newOperation.setOperationType(Operation.OperationType.приход); // Например, тип входной операции
            operationRepository.create(newOperation);
            System.out.println("Operation created: " + newOperation);

            // Операции с ProductMovement
            System.out.println("=== ProductMovement Operations ===");

            // 1. Создание перемещения товара
            ProductMovement newMovement = new ProductMovement();
            newMovement.setProductID(newProduct.getProductID());
            newMovement.setWarehouseID(newWarehouse.getWarehouseID());
            newMovement.setOperationID(newOperation.getOperationID());
            newMovement.setQuantity(15.0);
            newMovement.setDate(LocalDate.now());
            productMovementRepository.create(newMovement);
            System.out.println("Product Movement created: " + newMovement);

            // 2. Получение перемещения по ID
            ProductMovement fetchedMovement = productMovementRepository.findById(newMovement.getMovementID());
            System.out.println("Fetched Product Movement: " + fetchedMovement);

            // 3. Получение всех перемещений
            List<ProductMovement> movements = productMovementRepository.findAll();
            System.out.println("All Product Movements:");
            movements.forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
