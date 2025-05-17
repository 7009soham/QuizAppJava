<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Join Private Lobby</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 2rem;
        }
        input[type="text"] {
            padding: 0.5rem;
            font-size: 1rem;
            margin-right: 0.5rem;
        }
        button {
            padding: 0.5rem 1rem;
            font-size: 1rem;
            cursor: pointer;
        }
        .error {
            color: red;
            margin-top: 1rem;
        }
    </style>
</head>
<body>
<h2>Join a Private Lobby</h2>

<form action="/lobby/join-private" method="post">
    <input type="text" name="code" placeholder="Enter lobby code" required />
    <button type="submit">Join</button>
</form>

<c:if test="${not empty error}">
    <p class="error">${error}</p>
</c:if>
</body>
</html>
