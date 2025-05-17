<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Login</title>
  <style>
    body { font-family: sans-serif; text-align: center; padding: 50px; }
    input, button { padding: 10px; font-size: 16px; margin: 5px; }
  </style>
</head>
<body>
<h2>Enter Your Username to Start</h2>
<h2>Login</h2>
<form action="/login" method="post">
  <input type="text" name="username" placeholder="Enter your name" required />
  <button type="submit">Start</button>
</form>
</body>
</html>
