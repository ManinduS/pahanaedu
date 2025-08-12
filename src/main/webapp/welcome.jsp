<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String username = (session != null) ? (String) session.getAttribute("username") : null;
    if (username == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h1>Welcome, <%= username %>!</h1>
<p>You have successfully logged in to Pahana Edu.</p>

<form action="<%= request.getContextPath() %>/logout" method="get">
    <button type="submit">Logout</button>
</form>
</body>
</html>
