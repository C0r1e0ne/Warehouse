package org.example.model;

import java.util.Date;

public class ProductMovement {

    private Long movementID;
    private Long productID;
    private Long warehouseID;
    private Long operationID;
    private double quantity;
    private Date date;

    public Long getMovementID() {
        return movementID;
    }

    public void setMovementID(Long movementID) {
        this.movementID = movementID;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public Long getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(Long warehouseID) {
        this.warehouseID = warehouseID;
    }

    public Long getOperationID() {
        return operationID;
    }

    public void setOperationID(Long operationID) {
        this.operationID = operationID;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ProductMovement{" +
                "movementID=" + movementID +
                ", productID=" + productID +
                ", warehouseID=" + warehouseID +
                ", operationID=" + operationID +
                ", quantity=" + quantity +
                ", date=" + date +
                '}';
    }
}
