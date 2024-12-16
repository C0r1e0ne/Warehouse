<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Product" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Filter</title>
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
        .back-button {
            margin-bottom: 20px;
        }
        .back-button a {
            display: inline-block;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            font-size: 14px;
        }
        .back-button a:hover {
            background-color: #45a049;
        }
        form {
            text-align: center;
            margin-bottom: 20px;
        }
        form label {
            font-size: 14px;
            margin-right: 5px;
        }
        form input {
            padding: 8px;
            margin: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        form button {
            padding: 10px 20px;
            border: none;
            background-color: #4CAF50;
            color: white;
            border-radius: 4px;
            cursor: pointer;
        }
        form button:hover {
            background-color: #45a049;
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
        .message {
            text-align: center;
            margin: 20px 0;
            color: #333;
        }
    </style>
</head>
<body>
<h1>Product Filter</h1>
<div class="container">
    <div class="back-button">
        <a href="home.jsp">Back to Main Page</a>
    </div>

    <form method="get" action="product-filter">
        <label for="price">Price:</label>
        <input type="number" step="0.01" name="price" id="price" placeholder="Enter max price">
        <label for="quantity">Quantity:</label>
        <input type="number" name="quantity" id="quantity" placeholder="Enter min quantity">
        <button type="submit">Filter</button>
    </form>

    <%
        List<Product> products = (List<Product>) request.getAttribute("products");
        Integer currentPage = (Integer) request.getAttribute("currentPage");
        Integer totalPages = (Integer) request.getAttribute("totalPages");
    %>

    <% if (products != null) { %>
    <div class="table-container">
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Price</th>
                <th>Quantity</th>
            </tr>
            </thead>
            <tbody>
            <% for (Product product : products) { %>
            <tr>
                <td><%= product.getProductID() %></td>
                <td><%= product.getName() %></td>
                <td><%= product.getPrice() %></td>
                <td><%= product.getQuantity() %></td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>

    <div class="pagination">
        <% if (currentPage > 1) { %>
        <a href="product-filter?page=<%= currentPage - 1 %>&price=<%= request.getParameter("price") %>&quantity=<%= request.getParameter("quantity") %>">Previous</a>
        <% } %>
        <% for (int i = 1; i <= totalPages; i++) { %>
        <a href="product-filter?page=<%= i %>&price=<%= request.getParameter("price") %>&quantity=<%= request.getParameter("quantity") %>" class="<%= (i == currentPage) ? "active" : "" %>"><%= i %></a>
        <% } %>
        <% if (currentPage < totalPages) { %>
        <a href="product-filter?page=<%= currentPage + 1 %>&price=<%= request.getParameter("price") %>&quantity=<%= request.getParameter("quantity") %>">Next</a>
        <% } %>
    </div>
    <% } else { %>
    <div class="message">
        <p>Please enter filter values and click "Filter" to see results.</p>
    </div>
    <% } %>
</div>
</body>
</html>
