<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Customer</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/manage_items.css">
</head>
<body>
<h1>Customer</h1>
<div class="grid">
    <a class="card" href="<%=request.getContextPath()%>/addCustomer.jsp">➕ Add Customer</a>
    <a class="card" href="<%=request.getContextPath()%>/customers">👀 View Customers</a>
    <a class="card" href="<%=request.getContextPath()%>/customers?mode=manage">✏️ Edit Customers</a>
</div>
<p style="margin-top:16px;"><a href="welcome.jsp">← Back to Home</a></p>
</body>
</html>
