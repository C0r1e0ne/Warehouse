<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.model.ProductMovement" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Edit Product Movement</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 0;
      background-color: #f4f7fc;
    }
    h1 {
      text-align: center;
      color: #333;
      margin-top: 20px;
    }
    .container {
      max-width: 900px;
      margin: 20px auto;
      padding: 20px;
      background-color: #fff;
      border-radius: 8px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }
    .edit-form {
      margin-bottom: 20px;
      text-align: center;
    }
    .edit-form input {
      padding: 8px;
      margin: 5px;
      border: 1px solid #ccc;
      border-radius: 4px;
    }
    .edit-form button {
      padding: 10px 20px;
      border: none;
      background-color: #4CAF50;
      color: white;
      border-radius: 4px;
      cursor: pointer;
    }
    .edit-form button:hover {
      background-color: #45a049;
    }
  </style>
</head>
<body>
<h1>Edit Product Movement</h1>
<div class="container">
  <%
    ProductMovement pm = (ProductMovement) request.getAttribute("productMovement");
    if (pm != null) {
  %>
  <div class="edit-form">
    <h2>Edit Product Movement</h2>
    <form action="product-movement" method="post">
      <input type="hidden" name="id" value="${productMovement.movementID}">
      <label for="productID">Product ID:</label>
      <input type="text" id="productID" name="productID" value="${productMovement.productID}" required><br><br>
      <label for="warehouseID">Warehouse ID:</label>
      <input type="text" id="warehouseID" name="warehouseID" value="${productMovement.warehouseID}" required><br><br>
      <label for="operationID">Operation ID:</label>
      <input type="text" id="operationID" name="operationID" value="${productMovement.operationID}" required><br><br>
      <label for="quantity">Quantity:</label>
      <input type="text" id="quantity" name="quantity" value="${productMovement.quantity}" required><br><br>
      <label for="date">Date:</label>
      <input type="date" id="date" name="date" value="${productMovement.date}" required><br><br>
      <button type="submit">Update Product Movement</button>
    </form>
  </div>
  <%
  } else {
  %>
  <p>Error: Product Movement not found.</p>
  <%
    }
  %>
</div>
</body>
</html>
