<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.model.Warehouse" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Warehouse</title>
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
        .edit-warehouse-form {
            margin-bottom: 20px;
            text-align: center;
        }
        .edit-warehouse-form input {
            padding: 8px;
            margin: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .edit-warehouse-form button {
            padding: 10px 20px;
            border: none;
            background-color: #4CAF50;
            color: white;
            border-radius: 4px;
            cursor: pointer;
        }
        .edit-warehouse-form button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<h1>Edit Warehouse</h1>
<div class="container">
    <%
        Warehouse warehouse = (Warehouse) request.getAttribute("warehouse");
        if (warehouse != null) {
    %>
    <div class="edit-warehouse-form">
        <h2>Edit Warehouse</h2>
        <form action="edit-warehouse" method="post">
            <input type="hidden" name="id" value="<%= warehouse.getWarehouseID() %>">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="<%= warehouse.getName() %>" required><br><br>
            <label for="location">Location:</label>
            <input type="text" id="location" name="location" value="<%= warehouse.getLocation() %>" required><br><br>
            <button type="submit">Update Warehouse</button>
        </form>
    </div>
    <%
    } else {
    %>
    <p>Error: Warehouse not found.</p>
    <%
        }
    %>
</div>
</body>
</html>
