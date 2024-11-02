package org.example;

import org.example.di.ApplicationContext;
import org.example.interfaces.*;
import org.example.model.*;
import org.example.service.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContext();

        ProductService productService = context.getProductService();
        WarehouseService warehouseService = context.getWarehouseService();
        OperationService operationService = context.getOperationService();
        ProductMovementService productMovementService = context.getProductMovementService();
        UserService userService = context.getUserService();

        try {
            Product newProduct = new Product();
            newProduct.setName("Sample Product");
            newProduct.setPrice(100);
            newProduct.setQuantity(20);

            productService.addProduct(newProduct);
            System.out.println("Created Product: " + newProduct);

            Product fetchedProduct = productService.getProductById(newProduct.getProductID());
            System.out.println("Fetched Product: " + fetchedProduct);

            fetchedProduct.setPrice(120);
            productService.updateProduct(fetchedProduct);
            System.out.println("Updated Product: " + fetchedProduct);

            List<Product> allProducts = productService.getAllProducts();
            System.out.println("All Products:");
            for (Product product : allProducts) {
                System.out.println(product);
            }

            /*productService.deleteProduct(fetchedProduct.getProductID());
            System.out.println("Deleted Product ID: " + fetchedProduct.getProductID());*/

            Warehouse warehouse = new Warehouse();
            warehouse.setName("Main Warehouse");
            warehouse.setLocation("City Center");

            warehouseService.addWarehouse(warehouse);
            System.out.println("Created Warehouse: " + warehouse);

            Operation operation = new Operation();
            operation.setOperationType(Operation.OperationType.выдача);

            operationService.addOperation(operation);
            System.out.println("Created Operation: " + operation);

            ProductMovement movement = new ProductMovement();
            movement.setProductID(newProduct.getProductID());
            movement.setWarehouseID(warehouse.getWarehouseID());
            movement.setOperationID(operation.getOperationID());
            movement.setQuantity(10);
            movement.setDate(LocalDate.now());

            productMovementService.addProductMovement(movement);
            System.out.println("Created Product Movement: " + movement);

            Users user = new Users();
            user.setName("John Doe");
            user.setLogin("johndoe");
            user.setPassword("password123");

            userService.addUser(user);
            System.out.println("Created User: " + user);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
