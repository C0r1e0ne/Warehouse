<%@ page import="org.example.model.ProductMovement" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Product Movement</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f7fc;
      margin: 0;
      padding: 0;
    }
    .container {
      max-width: 900px;
      margin: 20px auto;
      padding: 30px;
      background-color: #fff;
      border-radius: 8px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }
    h1 {
      text-align: center;
      color: #333;
      margin-bottom: 20px;
    }
    .error-message {
      color: #d9534f;
      background-color: #f2dede;
      border: 1px solid #ebccd1;
      padding: 10px;
      border-radius: 4px;
      margin-bottom: 20px;
      text-align: center;
    }
    form {
      margin-top: 30px;
      display: flex;
      flex-direction: column;
    }
    label {
      margin-bottom: 5px;
      font-weight: bold;
    }
    input, button {
      padding: 10px;
      font-size: 16px;
      margin-bottom: 20px;
      border-radius: 4px;
      border: 1px solid #ddd;
    }
    input:focus, button:focus {
      outline: none;
      border-color: #4CAF50;
    }
    input[type="number"], input[type="date"] {
      width: 100%;
    }
    button {
      background-color: #4CAF50;
      color: white;
      cursor: pointer;
      border: none;
      font-weight: bold;
      transition: background-color 0.3s;
    }
    button:hover {
      background-color: #45a049;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 20px;
    }
    table th, table td {
      padding: 10px;
      text-align: left;
      border-bottom: 1px solid #ddd;
    }
    table th {
      background-color: #f4f4f4;
    }
    .back-button a {
      padding: 10px 20px;
      background-color: #4CAF50;
      color: white;
      text-decoration: none;
      border-radius: 4px;
      display: inline-block;
    }
    .back-button a:hover {
      background-color: #45a049;
    }

    /* CSS for buttons */
    .action-button {
      display: inline-block;
      background-color: #4CAF50;
      color: white;
      padding: 10px 20px;
      border-radius: 4px;
      text-decoration: none;
      font-size: 16px;
      text-align: center;
    }
    .action-button:hover {
      background-color: #45a049;
    }

    /* CSS for delete button */
    .delete-button {
      display: inline-block;
      background-color: #d9534f;
      color: white;
      padding: 10px 20px;
      border-radius: 4px;
      text-decoration: none;
      font-size: 16px;
      text-align: center;
    }
    .delete-button:hover {
      background-color: #c9302c;
    }
  </style>
</head>
<body>
<div class="container">
  <h1>Product Movement</h1>

  <% if (request.getAttribute("error") != null) { %>
  <div class="error-message">
    <%= request.getAttribute("error") %>
  </div>
  <% } %>

  <div class="back-button">
    <a href="home.jsp">Back to Main Page</a>
  </div>

  <h2>Add New Product Movement</h2>
  <form action="product-movement" method="post">
    <label for="productID">Product ID:</label>
    <input type="number" name="productID" id="productID" required><br>

    <label for="warehouseID">Warehouse ID:</label>
    <input type="number" name="warehouseID" id="warehouseID" required><br>

    <label for="operationID">Operation ID:</label>
    <input type="number" name="operationID" id="operationID" required><br>

    <label for="quantity">Quantity:</label>
    <input type="number" name="quantity" id="quantity" required><br>

    <label for="date">Date:</label>
    <input type="date" name="date" id="date" required><br>

    <button type="submit">Add Movement</button>
  </form>

  <h2>Product Movements List</h2>
  <table>
    <thead>
    <tr>
      <th>Movement ID</th>
      <th>Product ID</th>
      <th>Warehouse ID</th>
      <th>Operation ID</th>
      <th>Quantity</th>
      <th>Date</th>
      <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
      List<ProductMovement> productMovements = (List<ProductMovement>) request.getAttribute("productMovements");
      if (productMovements != null && !productMovements.isEmpty()) {
        for (ProductMovement pm : productMovements) {
    %>
    <tr>
      <td><%= pm.getMovementID() %></td>
      <td><%= pm.getProductID() %></td>
      <td><%= pm.getWarehouseID() %></td>
      <td><%= pm.getOperationID() %></td>
      <td><%= pm.getQuantity() %></td>
      <td><%= pm.getDate() %></td>
      <td>
        <form action="edit-product-movement" method="get" style="display:inline;">
          <input type="hidden" name="id" value="<%= pm.getMovementID() %>">
          <button type="submit" class="action-button">Edit</button>
        </form>
        <form action="product-movement" method="post" style="display:inline;">
          <input type="hidden" name="movementID" value="<%= pm.getMovementID() %>">
          <input type="hidden" name="action" value="delete">
          <button type="submit" class="delete-button" onclick="return confirm('Are you sure you want to delete this movement?');">Delete</button>
        </form>
      </td>
    </tr>
    <% } } else { %>
    <tr>
      <td colspan="7" style="text-align: center;">No product movements found.</td>
    </tr>
    <% } %>
    </tbody>
  </table>
</div>
</body>
</html>
