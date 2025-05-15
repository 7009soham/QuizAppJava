<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Quiz Game - Home</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: #f5f5f5;
            text-align: center;
            padding: 100px;
        }
        h1 {
            color: #4CAF50;
        }
        .button {
            background-color: #4CAF50;
            border: none;
            color: white;
            padding: 14px 28px;
            font-size: 18px;
            margin-top: 30px;
            cursor: pointer;
            border-radius: 8px;
        }
        .button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

<h1>Welcome to the Quiz Game!</h1>
<p>This is a test JSP page running via Spring Boot.</p>

<form action="/select-mode">
    <button class="button">Start Quiz</button>
</form>

</body>
</html>
