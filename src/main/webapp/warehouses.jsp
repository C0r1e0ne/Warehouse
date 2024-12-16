<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Warehouse" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Warehouses</title>
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
            overflow-x: auto; /* Горизонтальная прокрутка */
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
        .add-warehouse-form {
            margin-bottom: 20px;
            text-align: center;
        }
        .add-warehouse-form input {
            padding: 8px;
            margin: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .add-warehouse-form button {
            padding: 10px 20px;
            border: none;
            background-color: #4CAF50;
            color: white;
            border-radius: 4px;
            cursor: pointer;
        }
        .add-warehouse-form button:hover {
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
        @media (max-width: 600px) {
            table, th, td {
                display: block;
                width: 100%;
            }
            table thead {
                display: none;
            }
            table td {
                padding: 10px;
                border: none;
                position: relative;
            }
            table td::before {
                content: attr(data-label);
                position: absolute;
                left: 0;
                top: 0;
                font-weight: bold;
                background-color: #f4f4f4;
                padding: 5px;
            }
        }
    </style>
</head>
<body>
<h1>Warehouses</h1>
<div class="container">
    <% if (request.getAttribute("error") != null) { %>
    <div class="error-message">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>

    <div class="back-button">
        <a href="home.jsp">Back to Main Page</a>
    </div>

    <div class="add-warehouse-form">
        <h2>Add New Warehouse</h2>
        <form action="warehouses" method="post">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" required><br><br>
            <label for="location">Location:</label>
            <input type="text" id="location" name="location" required><br><br>
            <button type="submit">Add Warehouse</button>
        </form>
    </div>

    <div class="table-container">
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Location</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Warehouse> warehouses = (List<Warehouse>) request.getAttribute("warehouses");
                if (warehouses != null && !warehouses.isEmpty()) {
                    for (Warehouse warehouse : warehouses) {
            %>
            <tr>
                <td data-label="ID"><%= warehouse.getWarehouseID() %></td>
                <td data-label="Name"><%= warehouse.getName() %></td>
                <td data-label="Location"><%= warehouse.getLocation() %></td>
                <td data-label="Actions">
                    <a href="edit-warehouse?id=<%= warehouse.getWarehouseID() %>" class="edit-button">Edit</a>
                    <a href="warehouses?id=<%= warehouse.getWarehouseID() %>&action=delete"
                       onclick="return confirm('Are you sure you want to delete this warehouse?');" class="delete-button">Delete</a>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="4" style="text-align: center;">No warehouses found.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>

    <div class="pagination">
        <%
            int maxButtonsToShow = 5; // Количество кнопок для отображения
            Integer currentPage = (Integer) request.getAttribute("currentPage");
            Integer totalPages = (Integer) request.getAttribute("totalPages");

            if (currentPage != null && totalPages != null) {
                int startPage = Math.max(1, currentPage - maxButtonsToShow / 2);
                int endPage = Math.min(totalPages, startPage + maxButtonsToShow - 1);

                if (currentPage > 1) {
        %>
        <a href="warehouses?page=1">First</a>
        <a href="warehouses?page=<%= currentPage - 1 %>">Previous</a>
        <%
            }

            for (int i = startPage; i <= endPage; i++) {
        %>
        <a href="warehouses?page=<%= i %>" class="<%= (i == currentPage) ? "active" : "" %>"><%= i %></a>
        <%
            }

            if (currentPage < totalPages) {
        %>
        <a href="warehouses?page=<%= currentPage + 1 %>">Next</a>
        <a href="warehouses?page=<%= totalPages %>">Last</a>
        <%
                }
            }
        %>
    </div>
</div>
</body>
</html>