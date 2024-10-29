package org.example.model;

public class Operation {

    private Long operationID;

    public enum OperationType {
        списание,
        приход,
        выдача
    }

    private OperationType operationType;

    public Long getOperationID() {
        return operationID;
    }

    public void setOperationID(Long operationID) {
        this.operationID = operationID;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "operationID=" + operationID +
                ", operationType=" + operationType +
                '}';
    }
}
