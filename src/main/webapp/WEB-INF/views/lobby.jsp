<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Lobby Room</title>
    <meta http-equiv="refresh" content="5"> <%-- Auto-refresh every 5 sec --%>
</head>
<body>

<!-- ✅ Flash Messages -->
<c:if test="${not empty message}">
    <div style="color: green; font-weight: bold;">${message}</div>
</c:if>
<c:if test="${not empty error}">
    <div style="color: red; font-weight: bold;">${error}</div>
</c:if>

<!-- ✅ Lobby Info -->
<h2>Lobby Code: <strong>${lobby.code}</strong></h2>
<p>Lobby Type: ${lobby.type} | Host: ${lobby.hostUsername}</p>

<h3>Players Joined (${players.size()}):</h3>
<ul>
    <c:forEach var="player" items="${players}">
        <li>${player.username}</li>
    </c:forEach>
</ul>

<!-- ✅ Host: Start Quiz Button -->
<c:if test="${isHost && lobby.type eq 'MULTIPLAYER'}">
    <form method="post" action="/start-quiz">
        <input type="hidden" name="lobbyCode" value="${lobby.code}" />
        <button type="submit">Start Quiz</button>
    </form>
</c:if>

<!-- ✅ Waiting Text for 1v1 -->
<c:if test="${isHost && lobby.type eq '1V1' && players.size() < 2}">
    <p>⚔️ Waiting for opponent to join...</p>
</c:if>

<!-- ✅ General Note -->
<p style="margin-top: 20px; font-size: 0.9em; color: gray;">(Auto-refresh every 5 seconds)</p>

</body>
</html>
