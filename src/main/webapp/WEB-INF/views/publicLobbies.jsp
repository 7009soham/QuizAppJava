<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Public Lobbies</title>
</head>
<body>
<h2>Join a Public Lobby</h2>

<c:choose>
    <c:when test="${not empty publicLobbies}">
        <form action="/lobby/join" method="post">
            <select name="lobbyCode" required>
                <c:forEach var="lobby" items="${publicLobbies}">
                    <option value="${lobby.code}">
                        Code: ${lobby.code} | Host: ${lobby.hostUsername} | Players: ${lobby.players.size()}
                    </option>
                </c:forEach>
            </select>
            <button type="submit">Join Selected Lobby</button>
        </form>
    </c:when>
    <c:otherwise>
        <p>No active public lobbies at the moment.</p>
    </c:otherwise>
</c:choose>

</body>
</html>
