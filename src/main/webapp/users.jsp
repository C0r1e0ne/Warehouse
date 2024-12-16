<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Users" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Users</title>
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
    .search-form {
      display: flex;
      justify-content: space-between;
      margin-bottom: 20px;
    }
    .search-form input[type="text"] {
      width: 70%;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 4px;
    }
    .search-form button {
      padding: 10px 20px;
      border: none;
      background-color: #4CAF50;
      color: white;
      border-radius: 4px;
      cursor: pointer;
      font-size: 14px;
    }
    .search-form button:hover {
      background-color: #45a049;
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
<h1>Users</h1>
<div class="container">
  <div class="back-button">
    <a href="home.jsp">Back to Home</a>
  </div>

  <form class="search-form" method="get" action="users">
    <input type="text" name="search" placeholder="Search by name or login..." value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>">
    <button type="submit">Search</button>
  </form>

  <table>
    <thead>
    <tr>
      <th>ID</th>
      <th>Name</th>
      <th>Login</th>
    </tr>
    </thead>
    <tbody>
    <%
      List<Users> users = (List<Users>) request.getAttribute("users");
      if (users != null && !users.isEmpty()) {
        for (Users user : users) {
    %>
    <tr>
      <td><%= user.getId() %></td>
      <td><%= user.getName() %></td>
      <td><%= user.getLogin() %></td>
    </tr>
    <%
      }
    } else {
    %>
    <tr>
      <td colspan="3" style="text-align: center;">No users found.</td>
    </tr>
    <%
      }
    %>
    </tbody>
  </table>

  <div class="pagination">
    <%
      int currentPage = (Integer) request.getAttribute("currentPage");
      int totalPages = (Integer) request.getAttribute("totalPages");
      String searchQuery = request.getParameter("query");

      if (currentPage > 1) {
    %>
    <a href="users?page=<%= currentPage - 1 %>&query=<%= searchQuery != null ? searchQuery : "" %>">Previous</a>
    <%
      }

      for (int i = 1; i <= totalPages; i++) {
    %>
    <a href="users?page=<%= i %>&query=<%= searchQuery != null ? searchQuery : "" %>" class="<%= (i == currentPage) ? "active" : "" %>"><%= i %></a>
    <%
      }

      if (currentPage < totalPages) {
    %>
    <a href="users?page=<%= currentPage + 1 %>&query=<%= searchQuery != null ? searchQuery : "" %>">Next</a>
    <%
      }
    %>
  </div>
</div>
</body>
</html>
