<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pahana Edu - Login</title>
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
<div class="login-container">
    <h1>Pahana Edu Login</h1>

    <!-- Error message -->
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
    <div class="error-message"><%= error %></div>
    <%
        }
    %>

    <!-- Login form -->
    <form action="login" method="post">
        <label for="username">Username</label>
        <input type="text" id="username" name="username" placeholder="Enter username" required>

        <label for="password">Password</label>
        <input type="password" id="password" name="password" placeholder="Enter password" required>

        <button type="submit">Login</button>
    </form>

    <p class="note">Only authorized users can access the system.  use admin as username & 12345 password</p>
</div>
</body>
</html>
