<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Product" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Products</title>
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
        .error-message {
            color: #d9534f;
            background-color: #f2dede;
            border: 1px solid #ebccd1;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 20px;
            text-align: center;
        }
        .table-container {
            overflow-x: auto;
            margin-top: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table th, table td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        table th {
            background-color: #f4f4f4;
        }
        .pagination {
            margin-top: 20px;
            display: flex;
            justify-content: center;
        }
        .pagination a {
            padding: 8px 12px;
            margin: 0 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
            text-decoration: none;
            color: #333;
        }
        .pagination a.active {
            background-color: #4CAF50;
            color: white;
        }
        .pagination a:hover {
            background-color: #45a049;
            color: white;
        }
        .add-product-form {
            margin-bottom: 20px;
            text-align: center;
        }
        .add-product-form input {
            padding: 8px;
            margin: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .add-product-form button {
            padding: 10px 20px;
            border: none;
            background-color: #4CAF50;
            color: white;
            border-radius: 4px;
            cursor: pointer;
        }
        .add-product-form button:hover {
            background-color: #45a049;
        }
        .edit-button, .delete-button {
            padding: 6px 10px;
            border-radius: 4px;
            color: white;
            text-decoration: none;
            display: inline-block;
            font-size: 12px;
        }
        .edit-button {
            background-color: #4CAF50;
        }
        .edit-button:hover {
            background-color: #45a049;
        }
        .delete-button {
            background-color: #d9534f;
        }
        .delete-button:hover {
            background-color: #c9302c;
        }
        .back-button {
            display: block;
            margin-bottom: 20px;
            text-align: center;
        }
        .back-button a {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .back-button a:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<h1>Products</h1>
<div class="container">
    <div class="back-button">
        <a href="home.jsp">Back to Main Page</a>
    </div>

    <!-- Сообщение об ошибке -->
    <% if (request.getAttribute("error") != null) { %>
    <div class="error-message">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>

    <div class="add-product-form">
        <h2>Add New Product</h2>
        <form action="products" method="post">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" required><br><br>
            <label for="price">Price:</label>
            <input type="number" step="0.01" id="price" name="price" required><br><br>
            <label for="quantity">Quantity:</label>
            <input type="number" step="0.01" id="quantity" name="quantity" required><br><br>
            <button type="submit">Add Product</button>
        </form>
    </div>

    <div class="table-container">
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Product> products = (List<Product>) request.getAttribute("products");
                if (products != null && !products.isEmpty()) {
                    for (Product product : products) {
            %>
            <tr>
                <td><%= product.getProductID() %></td>
                <td><%= product.getName() %></td>
                <td><%= product.getPrice() %></td>
                <td><%= product.getQuantity() %></td>
                <td>
                    <a href="edit-product?id=<%= product.getProductID() %>" class="edit-button">Edit</a>
                    <a href="products?id=<%= product.getProductID() %>&action=delete"
                       onclick="return confirm('Are you sure you want to delete this product?');" class="delete-button">Delete</a>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="5" style="text-align: center;">No products found.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>

    <!-- Пагинация -->
    <div class="pagination">
        <%
            Integer currentPage = (Integer) request.getAttribute("currentPage");
            Integer totalPages = (Integer) request.getAttribute("totalPages");

            if (currentPage != null && totalPages != null) {
                if (currentPage > 1) {
        %>
        <a href="products?page=<%= currentPage - 1 %>">Previous</a>
        <%
            }

            for (int i = 1; i <= totalPages; i++) {
        %>
        <a href="products?page=<%= i %>" class="<%= (i == currentPage) ? "active" : "" %>"><%= i %></a>
        <%
            }

            if (currentPage < totalPages) {
        %>
        <a href="products?page=<%= currentPage + 1 %>">Next</a>
        <%
                }
            }
        %>
    </div>
</div>
</body>
</html>
