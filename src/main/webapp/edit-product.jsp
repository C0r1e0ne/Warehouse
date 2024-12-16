<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.model.Product" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Edit Product</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f7fc;
      margin: 0;
      padding: 0;
    }
    .container {
      max-width: 500px;
      margin: 50px auto;
      padding: 20px;
      background-color: #fff;
      border-radius: 8px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }
    h1 {
      text-align: center;
      color: #333;
    }
    form {
      display: flex;
      flex-direction: column;
    }
    label {
      margin-top: 10px;
      font-weight: bold;
    }
    input {
      padding: 10px;
      margin-top: 5px;
      border: 1px solid #ccc;
      border-radius: 4px;
    }
    button {
      margin-top: 20px;
      padding: 10px;
      background-color: #4CAF50;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }
    button:hover {
      background-color: #45a049;
    }
    .cancel {
      margin-top: 10px;
      background-color: #d9534f;
      text-align: center;
    }
    .cancel a {
      text-decoration: none;
      color: white;
      display: block;
      padding: 10px;
      border-radius: 4px;
    }
    .cancel a:hover {
      background-color: #c9302c;
    }
  </style>
</head>
<body>
<div class="container">
  <h1>Edit Product</h1>
  <%
    Product product = (Product) request.getAttribute("product");
    if (product == null) {
  %>
  <p style="color: red; text-align: center;">Product not found.</p>
  <% } else { %>
  <form action="edit-product" method="post">
    <input type="hidden" name="id" value="<%= product.getProductID() %>">
    <label for="name">Name:</label>
    <input type="text" id="name" name="name" value="<%= product.getName() %>" required>

    <label for="price">Price:</label>
    <input type="number" id="price" name="price" step="0.01" value="<%= product.getPrice() %>" required>

    <label for="quantity">Quantity:</label>
    <input type="number" id="quantity" name="quantity" step="0.01" value="<%= product.getQuantity() %>" required>

    <button type="submit">Save Changes</button>
  </form>
  <% } %>
  <div class="cancel">
    <a href="products">Cancel</a>
  </div>
</div>
</body>
</html>
