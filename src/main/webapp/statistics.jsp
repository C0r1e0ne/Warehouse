<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Statistics</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7fc;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }
        h1 {
            color: #333;
            margin-bottom: 30px;
        }
        .stats-container {
            text-align: center;
            margin-bottom: 30px;
        }
        .button {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            text-decoration: none;
        }
        .button:hover {
            background-color: #45a049;
        }
        .stat-item {
            margin: 10px 0;
            font-size: 18px;
        }
    </style>
</head>
<body>

<h1>Statistics</h1>

<div class="stats-container">
    <div class="stat-item">Total Operations: ${operationsCount}</div>
    <div class="stat-item">Total Users: ${usersCount}</div>

</div>

<a href="home.jsp" class="button">Back</a>

</body>
</html>
