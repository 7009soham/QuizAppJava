<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Quiz</title>
</head>
<body>
<h2>Welcome, ${username}</h2>
<p>Mode: <strong>${mode}</strong></p>

<h3>Question:</h3>
<p>${question}</p>

<form action="/submit-answer" method="post">
  <c:forEach var="opt" items="${options}">
    <label><input type="radio" name="answer" value="${opt}" required /> ${opt}</label><br/>
  </c:forEach>

  <input type="hidden" name="lobbyCode" value="${lobby.code}" />
  <button type="submit">Submit</button>
</form>
</body>
</html>
