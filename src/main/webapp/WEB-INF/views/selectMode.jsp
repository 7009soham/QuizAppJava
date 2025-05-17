<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Select Mode</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
        }
        fieldset {
            margin-bottom: 20px;
        }
        legend {
            font-weight: bold;
        }
        button {
            padding: 10px 20px;
            font-size: 16px;
        }
        .message {
            font-weight: bold;
        }
    </style>
</head>
<body>

<h2>Choose Your Quiz Setup</h2>

<c:if test="${not empty error}">
    <p class="message" style="color: red;">${error}</p>
</c:if>

<c:if test="${not empty message}">
    <p class="message" style="color: green;">${message}</p>
</c:if>

<c:if test="${not empty username}">
    <p>Welcome, <strong>${username}</strong>! 🎮</p>
</c:if>

<form action="/select-mode" method="post">
    <fieldset>
        <legend>Game Mode:</legend>
        <label><input type="radio" name="mode" value="NORMAL" required /> 🧠 Normal Mode</label><br/>
        <label><input type="radio" name="mode" value="ROAST" /> 🔥 Roast Mode</label>
    </fieldset>

    <fieldset>
        <legend>Lobby Type:</legend>
        <label><input type="radio" name="lobbyType" value="INDIVIDUAL" required /> 🧍 Individual</label><br/>
        <label><input type="radio" name="lobbyType" value="MULTIPLAYER" /> 👥 Multiplayer</label><br/>
        <label><input type="radio" name="lobbyType" value="1V1" /> ⚔️ 1v1 Competitive</label>
    </fieldset>

    <button type="submit">Enter Lobby</button>
</form>

</body>
</html>
