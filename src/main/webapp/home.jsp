<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7fc;
            margin: 0;
            padding: 0;
        }
        .navbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
        }
        .navbar .welcome {
            font-size: 16px;
        }
        .navbar .logout-btn {
            background-color: white;
            color: #4CAF50;
            padding: 5px 10px;
            text-decoration: none;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }
        .navbar .logout-btn:hover {
            background-color: #f4f7fc;
        }
        .content {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: calc(100vh - 50px);
        }
        .content h1 {
            color: #333;
            margin-bottom: 30px;
        }
        .button-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 20px;
            width: 300px;
        }
        .button {
            width: 100%;
            padding: 15px;
            font-size: 18px;
            font-weight: bold;
            color: white;
            background-color: #4CAF50;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            display: block;
        }
        .button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

<div class="navbar">
    <div class="welcome">
        Welcome, <%= session.getAttribute("username") != null ? session.getAttribute("username") : "Guest" %>!
    </div>
    <form action="logout" method="post" style="margin: 0;">
        <button type="submit" class="logout-btn">Logout</button>
    </form>
</div>

<div class="content">
    <h1>Dashboard</h1>
    <div class="button-container">
        <a href="statistics" class="button">Statistics</a>
        <a href="operations" class="button">Operations</a>
        <a href="products" class="button">Products</a>
        <a href="warehouses" class="button">Warehouses</a>
        <a href="users" class="button">Users</a>
        <a href="product-movement" class="button">Product movement</a>
        <a href="product-filter" class="button">Filter</a>
    </div>
</div>

</body>
</html>
