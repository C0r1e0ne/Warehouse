package org.example;

import org.example.dao.*;
import org.example.model.*;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            ProductDAO productDAO = new ProductDAO();
            Product newProduct = new Product();
            newProduct.setName("Laptop");
            newProduct.setPrice(1500);
            newProduct.setQuantity(10);

            productDAO.createProduct(newProduct);
            System.out.println("Product added: " + newProduct);

            Product fetchedProduct = productDAO.getProductById(newProduct.getProductID());
            if (fetchedProduct != null) {
                System.out.println("Fetched Product: " + fetchedProduct);
            }

            fetchedProduct.setName("Updated Laptop");
            fetchedProduct.setPrice(1700);
            productDAO.updateProduct(fetchedProduct);
            System.out.println("Product updated: " + fetchedProduct);

            List<Product> products = productDAO.getAllProducts();
            System.out.println("All Products:");
            for (Product product : products) {
                System.out.println(product);
            }

            productDAO.deleteProduct(fetchedProduct.getProductID());
            System.out.println("Product deleted: " + fetchedProduct.getProductID());

            WarehouseDAO warehouseDAO = new WarehouseDAO();
            Warehouse newWarehouse = new Warehouse();
            newWarehouse.setName("Main Warehouse");
            newWarehouse.setLocation("New York");

            warehouseDAO.createWarehouse(newWarehouse);
            System.out.println("Warehouse added: " + newWarehouse);

            Warehouse fetchedWarehouse = warehouseDAO.getWarehouseById(newWarehouse.getWarehouseID());
            if (fetchedWarehouse != null) {
                System.out.println("Fetched Warehouse: " + fetchedWarehouse);
            }

            fetchedWarehouse.setLocation("San Francisco");
            warehouseDAO.updateWarehouse(fetchedWarehouse);
            System.out.println("Warehouse updated: " + fetchedWarehouse);

            warehouseDAO.deleteWarehouse(fetchedWarehouse.getWarehouseID());
            System.out.println("Warehouse deleted: " + fetchedWarehouse.getWarehouseID());

            OperationDAO operationDAO = new OperationDAO();
            Operation newOperation = new Operation();
            newOperation.setOperationType(Operation.OperationType.приход);

            operationDAO.createOperation(newOperation);
            System.out.println("Operation added: " + newOperation);

            Operation fetchedOperation = operationDAO.getOperationById(newOperation.getOperationID());
            if (fetchedOperation != null) {
                System.out.println("Fetched Operation: " + fetchedOperation);
            }

            List<Operation> operations = operationDAO.getAllOperations();
            System.out.println("All Operations:");
            for (Operation operation : operations) {
                System.out.println(operation);
            }

            operationDAO.deleteOperation(fetchedOperation.getOperationID());
            System.out.println("Operation deleted: " + fetchedOperation.getOperationID());


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
